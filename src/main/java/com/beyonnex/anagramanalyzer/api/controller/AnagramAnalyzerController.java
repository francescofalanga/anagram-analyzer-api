package com.beyonnex.anagramanalyzer.api.controller;


import com.beyonnex.anagramanalyzer.manager.AnagramAnalyzerManager;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
@RestController
public class AnagramAnalyzerController {

	@ApiOperation(value = "Api that analyze if two words are an anagram")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message  = "OK"),
		@ApiResponse(code = 400, message  = "Bad Request"),
	})
	@GetMapping(path = "/anagram")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Boolean> checkIfWordsAreAnagram(
		@Validated @Size(max = 100, message = "Word must be "
			+ "less than 100 characters") @NotNull @RequestParam(name = "word1") String word1,
		@Validated @Size(max = 100, message = "Word must be "
			+ "less than 100 characters") @NotNull @RequestParam(name = "word2") String word2) {

		log.debug(String.format("API to detect anagram called : word1 %s and word2 %s", word1, word2));
		//call the lib and return the response
		var result = AnagramAnalyzerManager.checkIfWordsAreAnagramAndStoreResultInMemory(word1,word2);
		log.info(String.format("Result of the analisys : words %s an anagram", (result) ? "are" : "are not"));
		return ResponseEntity.ok(Boolean.valueOf(result));
	}


	@ApiOperation(value = "Api that returns the historical anagrams based on a input word")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message  = "OK"),
			@ApiResponse(code = 400, message  = "Bad Request"),
	})
	@GetMapping(path = "/anagram/historical")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<String>> getHistoricalAnagrams(
			@Validated
			@Size(max = 100, message = "Word must be less than 100 characters") @NotNull @RequestParam(name = "word") String word) {

		log.debug(String.format("API to retrieve historical anagrams : word %s", word));
		var result = AnagramAnalyzerManager.getHistoricalAnagramsByWord(word);
		log.info(String.format("List of historical anagram detected for the word %s is %s", word, String.join(",", result)));
		//call the lib and return the response
		return ResponseEntity.ok(result);
	}


}
