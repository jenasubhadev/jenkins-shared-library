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
	static Map<String, Long> stageTimes = [:]
	static Map<String, Long> stepTimes = [:]
  
	static void calculateStageTime(String stageName, Closure closure) {
	  def startTime = System.currentTimeMillis()
	  closure.call()
	  def endTime = System.currentTimeMillis()
	  def duration = endTime - startTime
	  stageTimes[stageName] = duration
	  println "Stage '${stageName}' took ${duration} milliseconds"
	}
  
	static void calculateStepTime(String stageName, String stepName, Closure closure) {
	  println "Step '${stepName}'"
	  def startTime = System.currentTimeMillis()
	  closure.call()
	  def endTime = System.currentTimeMillis()
	  def duration = endTime - startTime
	  stepTimes[stageName][stepName] = duration
	  println "Step '${stepName}' took ${duration} milliseconds"
	}
}