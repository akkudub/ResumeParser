package main;

import common.CV;
import common.Job;
import parser.CVParser;
import parser.JobParser;
import analyser.Analyser;

public class Controller {
	public static void main(String[] args) {
		CVParser p = new CVParser("src/resources/SuranjanaSengupta_Resume.pdf");
		p.setCVDetails();
		
		CV c = p.getCV();
		CV d = CV.fromString(c.toString());
		System.out.println(d.toString());
		int CVId = p.saveCV();
		System.out.println(CVId);
		
		JobParser jp = new JobParser("src/resources/JobExample4.pdf");
		jp.setJobDetails();
		
		Job j = jp.getJob();
		Job k = Job.fromString(j.toString());
		System.out.println(k.toString());
		int jobId = jp.saveJob();
		System.out.println(jobId);
		
		Analyser anal = new Analyser();
		anal.analyseCV(CVId);
		
	}
}