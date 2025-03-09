/* This code was created by Clayton Barkalge on 3/2/2025
   The purpose behind this code is to make a simple web page
   design to allow users to input the food they ate, the
   calories per serving, and how many servings they ate.
   This the simple layout, due to not having a design chosen.
*/
// This code was updated by Clayton Barklage on 3/9/2025
// added explanation comments

class HealthMetricLogging {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Enter your Weight (rounded and in lbs), Blood Pressure, Heart Rate, Breathing Rate, hours of sleep, and how many steps you have today:")

            val weight = readLine()?.toIntOrNull() ?: 0
            val bP = readLine()?.toIntOrNull() ?: 0 // bP = Blood Pressure
            val bpm = readLine()?.toIntOrNull() ?: 0 // bpm = Beats Per Minute
            val bR = readLine()?.toIntOrNull() ?: 0 // bR = Breathing Rate
            val HOS = readLine()?.toIntOrNull() ?: 0 // HOS = Hours of sleep
            val steps = readLine()?.toIntOrNull() ?: 0

            /* This section will be replaced with code to transport
               the data to the database when we have it completed
            */
            println(weight)
            println(bP)
            println(bpm)
            println(bR)
            println(HOS)
            println(steps)
        }
    }
}