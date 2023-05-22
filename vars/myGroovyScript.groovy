def measureStageTiming(stageName, closure) {
    def stageStartTime = System.currentTimeMillis()
    def stepTimings = [:]
    closure()
    def stageEndTime = System.currentTimeMillis()
    def stageDuration = stageEndTime - stageStartTime
    println "${stageName} Stage Execution Time: ${stageDuration} ms"

    // Generate HTML table for steps
    def stepsTable = "<table>"
    stepsTable += "<tr><th>Step</th><th>Duration (ms)</th></tr>"
    stepTimings.each { stepName, duration ->
        stepsTable += "<tr><td>${stepName}</td><td>${duration}</td></tr>"
    }
    stepsTable += "</table>"

    // Generate HTML report for the stage
    def stageReport = """<h2>${stageName}</h2>
        <p>Stage Execution Time: ${stageDuration} ms</p>
        ${stepsTable}<br>"""

    return [stageDuration, stageReport]
}

def call(config) {
    def stageTimings = [:]
    def stageReports = ""
    pipeline {
        agent any
        stages {
            stage('Stage 1') {
                steps {
                    script {
                        def [stageDuration, stageReport] = measureStageTiming('Stage 1') {
                            def stepStartTime = System.currentTimeMillis()
                            // Step 1 of Stage 1
                            def stepEndTime = System.currentTimeMillis()
                            def stepDuration = stepEndTime - stepStartTime
                            stepTimings['Step 1'] = stepDuration

                            stepStartTime = System.currentTimeMillis()
                            // Step 2 of Stage 1
                            stepEndTime = System.currentTimeMillis()
                            stepDuration = stepEndTime - stepStartTime
                            stepTimings['Step 2'] = stepDuration
                        }
                        stageTimings['Stage 1'] = stageDuration
                        stageReports += stageReport
                    }
                }
            }
            stage('Stage 2') {
                steps {
                    script {
                        def [stageDuration, stageReport] = measureStageTiming('Stage 2') {
                            def stepStartTime = System.currentTimeMillis()
                            // Step 1 of Stage 2
                            def stepEndTime = System.currentTimeMillis()
                            def stepDuration = stepEndTime - stepStartTime
                            stepTimings['Step 1'] = stepDuration

                            stepStartTime = System.currentTimeMillis()
                            // Step 2 of Stage 2
                            stepEndTime = System.currentTimeMillis()
                            stepDuration = stepEndTime - stepStartTime
                            stepTimings['Step 2'] = stepDuration
                        }
                        stageTimings['Stage 2'] = stageDuration
                        stageReports += stageReport
                    }
                }
            }
            // Add more stages as needed
        }
    }

    // Generate HTML report
    def htmlReport = """<html>
    <head>
    <title>Pipeline Timing Report</title>
    <style>
    table {
        border-collapse: collapse;
        width: 100%;
    }
    th, td {
        text-align: left;
        padding: 8px;
        border-bottom: 1px solid #ddd;
    }
    th {
        background-color: #f2f2f2;
    }
    </style>
    </head>
    <body>
    <h1>Pipeline Timing Report</h1>
    ${stageReports}
    </body>
    </html>
    """

    // Publish HTML report
    writeFile file: 'timing-report/report.html', text: htmlReport

    publishHTML(target: [
        allowMissing: false,
        alwaysLinkToLastBuild: true,
        keepAll: true,
        reportName: 'Pipeline Timing Report',
        reportDir: 'timing-report',
        reportFiles: 'report.html'
    ]) {
        archiveHTML()
    }
}

