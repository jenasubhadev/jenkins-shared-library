#!/usr/bin/env groovy

package org.example

class TimeMetrics {
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
  
	static void calculateStepTime(String stepName, Closure closure) {
	  def startTime = System.currentTimeMillis()
	  closure.call()
	  def endTime = System.currentTimeMillis()
	  def duration = endTime - startTime
	  stepTimes[stepName] = duration
	  println "Step '${stepName}' took ${duration} milliseconds"
	}
}