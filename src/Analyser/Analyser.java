package analyser;
import java.sql.*;   // Use classes in java.sql package
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.CV;
import common.Job;
import common.Ranking;
import common.Word;
import storage.Storage;

public class Analyser 
{
	static int matchedWords = 0; 
	static int totalWords;
	static double percentage;
	static String jobKeyWords;
	static String cvKeyWords;
	static String [] jobKeyWordsArray;
	static String [] cvKeyWordsArray;
	static Job job;
	static CV cv;
	
	Storage str = new Storage();
//	public static void main(String[] args) {
//		try 
//		{
//			// Step 1: Allocate a database "Connection" object
//			 Connection conn = DriverManager.getConnection(
//					"jdbc:mysql://localhost:3306/resumeparser", "root", "root"); // MySQL
//
//			// Step 2: Allocate a "Statement" object in the Connection
//			 Statement stmt = conn.createStatement();
//			// Step 3: Execute a SQL SELECT query, the query result
//			//  is returned in a "ResultSet" object.
//			
//			cv.setId(1);
//			cv.setName("Anand");
//			cv.setSkills("Java");
//			cv.setEducation("High School");
//			cv.setExperience("Entry Level");
//			cv.setLeadership("Leader");
//			cv.setAge(20);
//			
//			job.setId(1);
//			job.setTitle("Manager");
//			job.setSkills("Java");
//			job.setEducation("High School");
//			job.setExperience("Entry Level");
//			job.setLeadership("Leader");
//			job.setLocation("Singapore");
//			
//			compareCVWithJob(1);
//			
//			System.out.println("reached here");
////			String strSelect = "select * from stories";
////			System.out.println("The SQL query is: " + strSelect); // Echo For debugging
////			System.out.println();
////
////			ResultSet rset = stmt.executeQuery(strSelect);
////
////			// Step 4: Process the ResultSet by scrolling the cursor forward via next().
////			//  For each row, retrieve the contents of the cells with getXxx(columnName).
////			System.out.println("The records selected are:");
////			int rowCount = 0;
////			while(rset.next()) {   // Move the cursor to the next row
////				String title = rset.getString("title");
////				double maxPost = rset.getDouble("maxPost");
////				int    id   = rset.getInt("id");
////				System.out.println(title + ", " + maxPost + ", " + id);
////				++rowCount;
////			}
////			System.out.println("Total number of records = " + rowCount);
//
//		} catch(SQLException ex) {
//			ex.printStackTrace();
//		}
//	
//	}
//	private static Statement sqlStatement() {
//		try {
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resumeparser", 
//					"root", "root");
//			Statement stmt = conn.createStatement();
//			return stmt;
//		}
//		catch(SQLException ex) {
//			ex.printStackTrace();
//			return null;
//		}
//	}
//	
//	public static int totalWords(int jobId, String [] wordsArr)
//	{
//		int count = 0;
//		for(int i = 0; i < wordsArr.length; i++)
//		{
//			count++;
//		}
//		return count;
//	}
//	
//	
//    //compare all CVs with 1 job description
//	public static void compareCVWithJob(int jobId) throws SQLException
//    {
//		Statement stmt = sqlStatement();
//		String strSelect = "SELECT jobSkill FROM job WHERE job.id='" + jobId + "'";
//		String strSelect2 = "SELECT COUNT(*) AS rowcount FROM cv";
//		ResultSet rset1 = stmt.executeQuery(strSelect);
//		ResultSet rset2 = stmt.executeQuery(strSelect2);
//		jobKeyWords = job.getSkills();  //JobKeyWordsArray will be conceptualised | spearhead | .... | ....
//		jobKeyWordsArray = jobKeyWords.split(",");
//		totalWords = totalWords(jobId, jobKeyWordsArray);
//		int count = rset2.getInt("rowcount");
//		for(int i = 0; i < jobKeyWordsArray.length; i++)
//		{
//			for(int j = 0; j < count; j++)
//			{
//				cvKeyWords = cv.getSkills();   // get
//				cvKeyWordsArray = cvKeyWords.split(",");
//				for(int k = 0; k < cvKeyWordsArray.length; k++)
//				{
//					if(jobKeyWordsArray[i] == cvKeyWordsArray[k])
//					{
//						matchedWords++;
//					}
//					else
//					{
//						continue;
//					}
//				}
//				percentage = calculatePercentage(matchedWords, totalWords);
//			}
//		}
//		
//    }
//    
//    //compare all Jobs with 1 CV
//    public void compareJobWithCV(int CvId) throws SQLException
//    {
//    	Statement stmt = sqlStatement();
//		String strSelect = "SELECT cvSkill FROM cv WHERE cv.id='" + CvId + "'";
//		String strSelect2 = "SELECT FROM AS rowcount COUNT(*) job";
//		ResultSet rset1 = stmt.executeQuery(strSelect);
//		ResultSet rset2 = stmt.executeQuery(strSelect2);
//		cvKeyWords = cv.getSkills();  //JobKeyWordsArray will be conceptualised | spearhead | .... | ....
//		cvKeyWordsArray = cvKeyWords.split(",");
//		totalWords = totalWords(CvId, cvKeyWordsArray);
//		int count = rset2.getInt("rowcount");
//		for(int i = 0; i < cvKeyWordsArray.length; i++)
//		{
//			for(int j = 0; j < count; j++)  //no of records
//			{
//				jobKeyWords = job.getSkills();   // get
//				jobKeyWordsArray = jobKeyWords.split(",");
//				for(int k = 0; k < jobKeyWordsArray.length; k++)
//				{
//					if(cvKeyWordsArray[i] == jobKeyWordsArray[k])
//					{
//						matchedWords++;
//					}
//					else
//					{
//						continue;
//					}
//				}
//				percentage = calculatePercentage(matchedWords, totalWords);
//			}
//		}
//    }
//    
//    
//    public static double calculatePercentage(int matchedWords, int totalWords)
//    {	
//    	return matchedWords/totalWords*100;	
//    }
    
    public void analyseCV(int CVId) {
    	CV cv = str.getCV(CVId);
    	ArrayList<Job> jobsArray = str.getAllJobs();
    	
    	ArrayList<Ranking> rankArray = new ArrayList<Ranking>();
    	
    	for (int x = 0; x < jobsArray.size(); x++) {
    		Job job = jobsArray.get(x);
    		double matchPercentage = calculateMatchPercentage(cv, job);
    		Ranking rank = new Ranking();
    		rank.setCV(cv);
    		rank.setJobId(job.getId());
    		rank.setMatchPercentage(matchPercentage);
    		rankArray.add(rank);
    	}
    	str.saveRanking(rankArray);
    }
    
    public void analyseJob(int JobId) {
    	Job job = str.getJob(JobId);
    	ArrayList<CV> CVsArray = str.getAllCVs();
    	
    	ArrayList<Ranking> rankArray = new ArrayList<Ranking>();
    	
    	for (int x = 0; x < CVsArray.size(); x++) {
    		CV cv = CVsArray.get(x);
    		double matchPercentage = calculateMatchPercentage(cv, job);
    		Ranking rank = new Ranking();
    		rank.setCV(cv);
    		rank.setJobId(job.getId());
    		rank.setMatchPercentage(matchPercentage);
    		rankArray.add(rank);
    	}
    	str.saveRanking(rankArray);
    }
    
    private static double calculateMatchPercentage(CV cv, Job job) {
    	Map<String, List<Word>> cvContent = cv.getCvContentMap();
    	Map<String, List<Word>> jobContent = job.getJobContentMap();
    	
    	double matchPercentage = 0;
    	double matches = 0;
    	double numberOfKeywords = 0;
    	
    	for(Map.Entry<String, List<Word>> entry : jobContent.entrySet()) {
    		String key = entry.getKey();
    		List<Word> wordList = entry.getValue();
    		System.out.println(wordList);
    		for (int y = 0; y < wordList.size(); y++) {
    			for(Map.Entry<String, List<Word>> cvEntry : cvContent.entrySet()) {
    				List<Word> cvWordList = cvEntry.getValue();
    				for(int z = 0; z < cvWordList.size(); z++) {
    					if(cvWordList.get(z).toString().equalsIgnoreCase(wordList.get(y).toString())){
    						matches += 1.0; 
    					}
    				}
    			}
//    			if(cvContent.get(key)!= null && cvContent.get(key).contains(wordList.get(y))) {
//    				List<Word> cvWordList = cvContent.get(key);
//    				for(int z = 0; z < cvWordList.size(); z++) {
//    					if(cvWordList.get(z).toString().equalsIgnoreCase(wordList.get(y).toString())){
//    						matches += 1.0; 
//    					}
//    				}
//    				
//    			}
    		}
			numberOfKeywords += wordList.size();
    	}
    	
    	matchPercentage = matches/numberOfKeywords;
    	
    	System.out.println("Matching keywords: " + matches);
    	System.out.println("Total Keywords: " + numberOfKeywords);
    	System.out.println("Match Percentage for CV" + cv.getId() + " and Job" + job.getId() 
    			+ " is " + matchPercentage);
    	
    	return matchPercentage;
    }
}
