package br.com.labs;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping
public class UserResource {

	private UserRepository userRepository;
	
	public UserResource(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping
	public ResponseEntity<User> create(@Valid @RequestBody User user) {
		userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();

		return ResponseEntity.created(location).body(user);
	}
	
	@GetMapping
	public ResponseEntity<Page<User>> index(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return ResponseEntity.ok(userRepository.findAll(pageable));
	}
}
