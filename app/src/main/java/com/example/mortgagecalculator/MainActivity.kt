package com.example.mortgagecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mortgagecalculator.ui.theme.MortgageCalculatorTheme
import java.text.NumberFormat
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MortgageCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MortgageCalculatorLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MortgageCalculatorLayout() {
    var loanAmountInput by remember { mutableStateOf("") }
    var interestRateInput by remember { mutableStateOf("") }
    var yearsInput by remember { mutableStateOf("") }

    val loanAmount = loanAmountInput.toIntOrNull() ?: 0
    val interestRate = (interestRateInput.toDoubleOrNull() ?: 0.0) / 100.0
    val years = yearsInput.toIntOrNull() ?: 0

    val monthlyPayment = calculateMortgage(loanAmount, interestRate / 12, years * 12)

    Column(
        modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        TextField(
            value = loanAmountInput,
            leadingIcon = { Icon(
                painter = painterResource(R.drawable.money),
                contentDescription = stringResource(R.string.loan_amount)
            ) },
            onValueChange = { loanAmountInput = it },
            singleLine = true,
            label = { Text(stringResource(R.string.loan_amount)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        TextField(
            value = interestRateInput,
            leadingIcon = { Icon(
                painter = painterResource(R.drawable.percent),
                contentDescription = stringResource(id = R.string.interest_rate)
            ) },
            onValueChange = { interestRateInput = it },
            singleLine = true,
            label = { Text(stringResource(R.string.interest_rate)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        TextField(
            value = yearsInput,
            leadingIcon = { Icon(
                painter = painterResource(R.drawable.calendar),
                contentDescription = stringResource(id = R.string.years)
            ) },
            onValueChange = { yearsInput = it },
            singleLine = true,
            label = { Text(stringResource(R.string.years)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Text(
            text = "Monthly Payment: ${monthlyPayment}",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}

private fun calculateMortgage(p: Int, r: Double, n: Int): String {
    var mortgage = p * ( (r*(1.0 + r).pow(n)) / ((1.0 + r).pow(n) - 1) )
    if (mortgage.isNaN()) mortgage = 0.0
    return NumberFormat.getCurrencyInstance().format(mortgage)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MortgageCalculatorLayoutPreview() {
    MortgageCalculatorTheme {
        MortgageCalculatorLayout()
    }
}