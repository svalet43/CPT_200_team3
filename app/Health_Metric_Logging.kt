/* This code was created by Clayton Barkalge on 3/2/2025
   The purpose behind this code is to make a simple web page
   design to allow users to input the food they ate, the
   calories per serving, and how many servings they ate.
   This the simple layout, due to not having a design chosen.
*/
// This code was updated by Clayton Barklage on 4/13/2025
// added explanation comments

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HealthMetricLogging : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_metric_logging_layout)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val bloodPressureInput = findViewById<EditText>(R.id.bloodPressureInput)
        val bpmInput = findViewById<EditText>(R.id.bpmInput)
        val sleepInput = findViewById<EditText>(R.id.sleepInput)
        val stepsInput = findViewById<EditText>(R.id.stepsInput)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val weight = weightInput.text.toString()
            val bloodPressure = bloodPressureInput.text.toString()
            val bpm = bpmInput.text.toString()
            val sleep = sleepInput.text.toString()
            val steps = stepsInput.text.toString()

            // Display the input data as a Toast message
            Toast.makeText(this, "Weight: $weight\nBlood Pressure: $bloodPressure\nBPM: $bpm\nHours of Sleep: $sleep\nSteps Taken: $steps", Toast.LENGTH_LONG).show()
        }
        /*
        This area of the code was originally designed to hold the Gemini ai
        to make suggestions to the user for better health, along with a graph
        that would display the percent of cals for the day along with food groups.
        */
    }
}