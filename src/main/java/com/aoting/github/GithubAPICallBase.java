package com.aoting.github;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base class for Github RESTful API
 * Extended class can use the basic setting from this class.
 *
 */
public abstract class GithubAPICallBase {
	static final Logger logger = LogManager.getLogger(GithubAPICallBase.class);
	
	public static final String GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json";
	
	protected static final String hostName = "api.github.com";
	protected static final int port = 443;
	protected static final String scheme = "https";
	
	protected String owner;
	protected String repo;
	
	protected String username;
	protected String password;
	
	// Store for display summary
	protected JsonNode responseContent;
	
	public GithubAPICallBase(String owner, String repo) {
		this.owner = owner;
		this.repo = repo;
	}
	
	/**
	 * Execute the request
	 * @return String API response content
	 */
	public String execute() {
		responseContent = request();
		return responseContent != null ? responseContent.toString() : null;
	}
	
	/**
	 * Not display anything
	 */
	public void displaySummary() {}
	
	/**
	 * Use HttpClient making request to Github API
	 * @return parsed response from Github as JsonNode
	 */
	protected JsonNode request() {
		HttpClient client = getHttpClient();
		HttpHost host = new HttpHost(hostName, port, scheme);
		try {
			HttpResponse response = client.execute(host, createHttpRequest());
			logger.debug(response.getStatusLine().getStatusCode());
			if (response.getEntity() != null) {
				ObjectMapper om = new ObjectMapper();
				JsonNode jsnode = om.readTree(response.getEntity().getContent());
				logger.debug(jsnode.toString());
				return jsnode;
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected HttpClient getHttpClient() {
		final HttpClient client = HttpClientBuilder.create().build();
		return client;
	}
	
	/**
	 * Should be abstract to pull request
	 * @return
	 */
	abstract protected HttpRequest createHttpRequest();
	
	protected String getHost() {
        return scheme + "://" + hostName + ":" + port;
    }
}
