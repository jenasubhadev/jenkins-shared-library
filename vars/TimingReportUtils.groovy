// timing-report-library/TimingReportUtils.groovy
def call() {
  def generateTimingReport() {
	def reportContent = """
      <html>
      <head>
        <title>Timing Report</title>
      </head>
      <body>
        <h1>Timing Report</h1>
        <h2>Stage Times:</h2>
        <ul>
    """

	TimeMetrics.stageTimes.each { stage, duration ->
	  reportContent += "<li>Stage '${stage}' took ${duration} milliseconds</li>"
	}

	reportContent += """
        </ul>
        <h2>Step Times:</h2>
        <ul>
    """

	TimeMetrics.stepTimes.each { step, duration ->
	  reportContent += "<li>Step '${step}' took ${duration} milliseconds</li>"
	}

	reportContent += """
        </ul>
      </body>
      </html>
    """

	writeFile file: 'timing_report.html', text: reportContent
  }

  return this
}
