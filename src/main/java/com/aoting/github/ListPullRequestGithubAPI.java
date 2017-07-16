package com.aoting.github;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class ListPullRequestGithubAPI extends GithubAPICallBase {
	// state query parameter, default to "all"
	private String state = "all";
	
	public ListPullRequestGithubAPI(String owner, String repo) {
		super(owner, repo);
	}
	
	public ListPullRequestGithubAPI(String owner, String repo, String state) {
		super(owner, repo);
		this.state = state;
	}
	
	public String execute() {
		responseContent = this.request();
		return responseContent != null ? responseContent.toString() : null;
	}
	
	@Override
	public void displaySummary() {
		if (responseContent != null && responseContent.isArray()) {
			responseContent = (ArrayNode)responseContent;
			
			System.out.println("Got " + responseContent.size() + " pull requests.");
			StreamSupport.stream(responseContent.spliterator(), false)
			.forEach(node -> {
				System.out.println("ID:" + node.get("id"));
				System.out.println("URL:" + node.get("url"));
				System.out.println("Number: " + node.get("number"));
			});
		}
	}
	
	public List<String> getAllMergeRequestFromResponse() {
		List<String> mergeNumbers = new ArrayList<>();
		StreamSupport.stream(responseContent.spliterator(), false)
		.forEach(node -> {
			mergeNumbers.add(node.get("number").asText());
		});
		return mergeNumbers;
	}
	
	/**
	 * Create a GET request against pull request API
	 */
	@Override
	protected HttpRequest createHttpRequest() {
        HttpGet httpGet = new HttpGet(getHost() + "/repos/" + owner + "/" + repo + "/pulls?state=" + state);
        httpGet.setHeader("Accept", GITHUB_ACCEPT_HEADER);
        if (username != null && password != null) {
        	httpGet.setHeader("Authentication", AuthenticationUtils.getBasicAuthentication(username, password));
        }
        return httpGet;
    }
}
