package com.jean.salestax.controller;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.model.enums.ProductOrigin;
import com.jean.salestax.model.enums.TypeProduct;
import com.jean.salestax.service.PurchaseService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PurchaseController.class)
@AutoConfigureMockMvc
public class PurchaseControllerTest {

	static final String API = "/api/products";
	static final MediaType JSON = MediaType.APPLICATION_JSON;

	@Autowired
	MockMvc mvc;
	
	@MockBean
	PurchaseService service;
	
	@Test
	public void requestSuccess() throws Exception {

		List<PurchaseDTO> listDto = new ArrayList<PurchaseDTO>();

		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1)
				.type(TypeProduct.BOOKS).name("book").build();
		listDto.add(dto);

		MockHttpServletRequestBuilder request = buildMvcMock(listDto);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void RequestErrorDueToListEmpty() throws Exception {
		List<PurchaseDTO> listDto = new ArrayList<PurchaseDTO>();
		Throwable error = runRequest(buildMvcMock(listDto));

		Assertions.assertThat(error).hasMessageContaining("List cannot be empty");
	}
	
	@Test
	public void RequestErrorDueToNullImput() throws Exception {
		List<PurchaseDTO> listDto = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(null).quantity(1)
				.type(TypeProduct.BOOKS).name("book").build();
		
		listDto.add(dto);
		
		Throwable error = runRequest(buildMvcMock(listDto));
		
		Assertions.assertThat(error).hasMessageContaining("Price cannot be null");
	}
	
	@Test
	public void RequestErrorDueToNegativeImput() throws Exception {
		List<PurchaseDTO> listDto = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(19.90).quantity(-1)
				.type(TypeProduct.BOOKS).name("book").build();
		
		listDto.add(dto);
		
		Throwable error = runRequest(buildMvcMock(listDto));
		
		Assertions.assertThat(error).hasMessageContaining("Quantity cannot be negative or zero");
	}
	
	@Test
	public void RequestErrorDueToEmptyNameImput() throws Exception {
		List<PurchaseDTO> listDto = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(19.90).quantity(1)
				.type(TypeProduct.BOOKS).name("").build();
		
		listDto.add(dto);
		
		Throwable error = runRequest(buildMvcMock(listDto));
		
		Assertions.assertThat(error).hasMessageContaining("Name cannot be empty");
	}
	
	private MockHttpServletRequestBuilder buildMvcMock(List<PurchaseDTO> listDto) throws JsonProcessingException {
		
		String json = new ObjectMapper().writeValueAsString(listDto);
		
		return MockMvcRequestBuilders.post(API.concat("/purchase_summary"))
				.accept(JSON).contentType(JSON).content(json);
	}
	
	private Throwable runRequest(MockHttpServletRequestBuilder request) {
		return Assertions.catchThrowable( () -> mvc.perform(request));
	}
}
