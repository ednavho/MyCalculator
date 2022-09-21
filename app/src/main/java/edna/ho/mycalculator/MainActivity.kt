// learning from euTutorials on Youtube

package edna.ho.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

/*
MainActivity class dictates functioning of button clicks whether it be a digit, operator, or
equal sign. It contains functions that perform textView changes restricted by basic calculator
functionality.
 */
class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    /*
    param: (view) button with digit
    append button view's text to the tvInput textview
     */
    fun onDigit(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.append((view as Button).text)
        lastNumeric = true

    }

    /*
    param: (view) clear button
    reset textview text to an empty string to present nothing on screen
     */
    fun onClear(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    /*
    param: (view) decimal button
    only if the last char is a digit, append the decimal
     */
    fun onDecimalPoint(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    /*
    param: (view) equal button
    calculates the total value of the first value combined with the second value entered depending
    on the operator pressed. Accounts for a negative first value as prefix.

    throws ArithmeticException error if dividing by 0
     */
    fun onEqual(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        var prefix = ""

        //number must be last button to be pressed to continue with equal operation
        if (lastNumeric) {
            //store the calculation to be totaled
            var tvValue = tvInput.text.toString()

            try {
                //if the first value is negative, store in prefix in start the string after minus
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                //if subtracting, get both values on both sides of -
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //if there was a negative in front, add it back to first value after splitting
                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    //set new tvInput to difference of first and second val
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }

                //if multiplying, get both values on both sides of *
                else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //if there was a negative in front, add it back to first value after splitting
                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    //set new tvInput to product of first and second val
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

                //if adding, get both values on both sides of +
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //if there was a negative in front, add it back to first value after splitting
                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    //set new tvInput to sum of first and second val
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

                //if dividing, get both values on both sides of /
                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    //if there was a negative in front, add it back to first value after splitting
                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    //set new tvInput to quotient of first and second val
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    /*
    param: (result) the total after calculation
    removes the .0 from the total after calculation.

    does not account for .0000 and on because it only takes off the last two chars
     */
    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length -2)
        }
        return value
    }

    /*
    param: (view) operator button
    checks if the last char entered was a digit and if there were no other operators pressed
    beforehand, excluding any negative sign in the front. if both condition are passed, append the
    operator that the button is responsible for.
     */
    fun onOperator(view: View) {
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    /*
    param: (value) the current calculation on the textview to be calculated
    returns: false if there is only a negative sign in the front
    true if the textview text contains any other operator
     */
    private fun isOperatorAdded(value: String) : Boolean {
        //the first - acts as a negative sign, so it shouldn't be taken as an operator
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+")
                    || value.contains("-")
        }
    }
}