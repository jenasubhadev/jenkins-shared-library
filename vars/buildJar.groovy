#!/usr/bin/env groovy 

import org.example.TimeMetrics

def call(String imageName) {
	return new TimeMetrics(this).buildDockerImage(imageName)
	
}