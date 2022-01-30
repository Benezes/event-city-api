package com.devsuperior.bds02.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.service.exceptions.DatabaseException;
import com.devsuperior.bds02.service.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public List<CityDTO> findAllSorted() {
		return cityRepository.findAll(Sort.by("name")).stream().map(CityDTO::new).collect(Collectors.toList());
	}

	public CityDTO insert(CityDTO dto) {
		City entity = cityRepository.save(new City(dto));
		return new CityDTO(entity);
	}

	public void deleteById(Long id) {
		try {
			cityRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
