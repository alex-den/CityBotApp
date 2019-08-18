package com.example.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.entity.City;
import com.example.repository.CityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

	private static final String NOT_EXIST_NAME_ERR_MSG_FORMAT = "City with this Name %s not exist";
	private static final String NOT_EXIST_ID_ERR_MSG_FORMAT = "City with this Id %d not exist";
	private static final String ANSWER_CONTENT = ". I have some information: ";

	private final CityRepository cityRepository;

	@Override
	public City getCityByName(String name) {
		return cityRepository.findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(String.format(NOT_EXIST_NAME_ERR_MSG_FORMAT, name)));
	}

	@Override
	public City getCityById(long id) {
		return cityRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format(NOT_EXIST_ID_ERR_MSG_FORMAT, id)));
	}

	@Override
	@Transactional
	public City addCity(City city) {
		if (!cityRepository.existsByName(city.getName())) {
			return cityRepository.save(city);
		} else {
			return this.getCityByName(city.getName());
		}
	}

	@Override
	@Transactional
	public void removeCity(long id) {
		City deleteCity = this.getCityById(id);
		cityRepository.delete(deleteCity);
	}

	@Override
	@Transactional
	public City updateCity(City newCity) {
		City updateCity = this.getCityById(newCity.getId());
		updateCity.setName(newCity.getName());
		updateCity.setDescription(newCity.getDescription());
		return cityRepository.save(updateCity);
	}

	@Override
	public String getInfoAboutCityByName(String name) {
		Optional<City> city = cityRepository.findByName(name);
		StringBuilder info = new StringBuilder();
		if (city.isPresent()) {
			info.append(city.get().getName());
			info.append(ANSWER_CONTENT);
			info.append(city.get().getDescription());
		}
		return info.toString();
	}
}
