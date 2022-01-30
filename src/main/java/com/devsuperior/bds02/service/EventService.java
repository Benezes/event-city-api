package com.devsuperior.bds02.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.repository.EventRepository;
import com.devsuperior.bds02.service.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private CityRepository cityRepository;

	public EventDTO updateById(Long id, EventDTO dto) {

		EventDTO old = findById(id);
		City city = cityRepository.findById(dto.getCityId()).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		BeanUtils.copyProperties(dto, old, "id");
		Event event = eventRepository.save(new Event(old, city));
		return new EventDTO(event);
	}

	private EventDTO findById(Long id) {
		return new EventDTO(
				eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id)));
	}

}
