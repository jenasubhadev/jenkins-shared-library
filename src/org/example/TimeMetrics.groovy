#!/usr/bin/env groovy

package org.example



class TimeMetrics implements Serializable {
	
	def script 
	TimeMetrics(script) {
		this.script = script
	}
	
	def buildDockerImage(String imageName) {
		script.sh "echo $imageName"
	}
}