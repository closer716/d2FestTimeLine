package templates.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "user")
public class User {
	
	@Id
	private String id;
	private String name;
	private String address;
	
	public User(String name, String address) {
		this.name = name;
		this.address = address;
	}
}
