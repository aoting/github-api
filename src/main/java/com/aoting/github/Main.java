package com.aoting.github;

import java.io.Console;

public class Main {
	public static void main(String[] args) {
		String owner = "aoting";
		String repo = "machine-learning-exercise";
		
		Console console = System.console();
		
		// Get owner and repo
		System.out.println("Enter the repository owner (default: aoting)");
		String line = console.readLine();
		if (!line.equals(""))
			owner = line;
		System.out.println("Enter repository name (default: machine-learning-excercise)");
		line = console.readLine();
		if (!line.equals(""))
			repo = line;

		String commandLine = "";
		MergePullRequestGithubAPI mergeRequest = null;
		
		while(!commandLine.equals("0")) {
			// Console App Menu
			System.out.println("/********************************/");
			System.out.println("Enter number before the commands: ");
			System.out.println("Available Commands: ");
			System.out.println("1) Check all merge requests");
			System.out.println("2) Check closed merge requests");
			System.out.println("3) Check open merge requests");
			System.out.println("4) Merge an open pull request");
			System.out.println("5) Merge all open pull requests");
			System.out.println("0) Exit Program");
			System.out.println("/********************************/");
			
			line = console.readLine();
			int command = Integer.parseInt(line);
			GithubAPICallBase listMR = null;
			String state = "all";
			switch(command) {
			case 0:
				System.exit(0);
			case 1:
				break;
			case 2:
				state = "closed";
				break;
			case 3:
				state = "open";
				break;
			case 4:	//Merge specific merge request by number
				if (mergeRequest == null) {
					System.out.println("Please enter username and password on github");
					System.out.println("Username:");
					String username = console.readLine();
					char[] passwordArray = console.readPassword("Enter your password: ");
					String password = new String(passwordArray);
					mergeRequest = new MergePullRequestGithubAPI(owner, repo, username, password);
				}
				System.out.println("Merge number you would like to merge:");
				mergeRequest.setMergeNumber(console.readLine());
				System.out.println("Merge title:");
				mergeRequest.setMergeTitle(console.readLine());
				System.out.println("Merge description:");
				mergeRequest.setMergeDescription(console.readLine());
				System.out.println("Merging...");
				mergeRequest.execute();
				break;
				
			case 5:	// Merge all open merge request
				if (mergeRequest == null) {
					System.out.println("Please enter username and password on github");
					System.out.println("Username:");
					String username = console.readLine();
					char[] passwordArray = console.readPassword("Enter your password: ");
					String password = new String(passwordArray);
					mergeRequest = new MergePullRequestGithubAPI(owner, repo, username, password);
				}
				
				listMR = new ListPullRequestGithubAPI(owner, repo, "open");
				listMR.execute();
				System.out.println("Merge title:");
				mergeRequest.setMergeTitle(console.readLine());
				System.out.println("Merge description:");
				mergeRequest.setMergeDescription(console.readLine());
				
				System.out.println("Merging...");
				for (String number : ((ListPullRequestGithubAPI)listMR).getAllMergeRequestFromResponse()) {
					mergeRequest.setMergeNumber(number);
					mergeRequest.execute();
					mergeRequest.displaySummary();
				}
				break;
			default:
				System.out.println("Invalid Command!");
			}
			
			listMR = new ListPullRequestGithubAPI(owner, repo, state);
			if (command > 0 && command < 4) {
				listMR.execute();
				listMR.displaySummary();
			}
		}
	}
}
