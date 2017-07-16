package com.aoting.github;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 * Github merge request API class 
 * Only merge a single merge request
 * Basic authentication is used at API request
 * Username and password must be provided
 */
public class MergePullRequestGithubAPI extends GithubAPICallBase {
	
	private String mergeRequestNumber;
	private String mergeTitle;
	private String mergeDescription;

	public MergePullRequestGithubAPI(String owner, String repo, String username, String password) {
		super(owner, repo);
		this.username = username;
		this.password = password;
	}
	
	public void setMergeNumber(String mergeRequestNumber) {
		this.mergeRequestNumber = mergeRequestNumber;
	}
	public void setMergeTitle(String mergeTitle) {
		this.mergeTitle = mergeTitle;
	}
	public void setMergeDescription(String mergeDescription) {
		this.mergeDescription = mergeDescription;
	}
	
	@Override
	public void displaySummary() {
		if (responseContent != null && responseContent.isObject()) {
			responseContent = (ObjectNode)responseContent;
			System.out.println(responseContent.toString());
		} else {
			System.out.println("There is an error performing a merge request");
		}
	}
	
	/**
	 * Create a PUT request to merge request merging interface
	 */
	@Override
	protected HttpRequest createHttpRequest() {
		HttpPut httpPut = new HttpPut(getHost() + "/repos/" + owner + "/" + repo + "/pulls/" + mergeRequestNumber + "/merge");
		httpPut.setHeader("Accept", GITHUB_ACCEPT_HEADER);
        if (username != null && password != null) {
        	httpPut.setHeader("Authorization", AuthenticationUtils.getBasicAuthentication(username, password));
        }
        StringEntity requestEntity = new StringEntity(getMergeRequestPayload(), ContentType.APPLICATION_JSON);
        httpPut.setEntity(requestEntity);
        return httpPut;
	}
	
	/**
	 * Write a JSON payload for merging request.
	 * See  {@link https://developer.github.com/v3/pulls/#merge-a-pull-request-merge-button}
	 * 
	 * Able to set these parameter
	 * commit_title
	 * mergeDescription
	 * 
	 * In this exercise, we do SHA constraint
	 * sha
	 * 
	 * Use default: merge
	 * merge_method
	 * @return
	 */
	private String getMergeRequestPayload() {
		return String.format("{\"commit_title\": \"%s\", \"commit_message\": \"%s\"}", mergeTitle, mergeDescription);
	}
}
