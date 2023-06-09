package br.com.metaway.petshop.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.metaway.petshop.models.Client;
import br.com.metaway.petshop.models.Contact;
import br.com.metaway.petshop.repositories.ClientRepository;
import br.com.metaway.petshop.repositories.ContactRepository;
import br.com.metaway.petshop.repositories.dtos.ContactData;

@Service
public class ContactService {
	@Autowired
	private ContactRepository repository;

	@Autowired
	private ClientRepository clients;
	
	@CachePut(value = "contact", key = "#contact.id")
	@CacheEvict(value = "allContacts", allEntries = true)
	public ContactData create (Contact contact) {
		// Check if the Client exists
		String cpf = contact.getClient().getCpf();
	    Optional<Client> optionalClient = clients.findByCpf(cpf);
	
	    if (optionalClient.isEmpty()) {
	        return null;
	    }
	    
	    // After, if exists, get it and set
	    Client client = optionalClient.get();
	    contact.setClient(client);
	    
	    // Then save
	    Contact newContact = repository.save(contact);
	    
	    
	    // And sends back the Data
		return new ContactData(newContact.getId(), 
							   newContact.getClient().getCpf(),
							   newContact.getType(),
							   newContact.getValue(),
							   newContact.getCurrency());
	}
	
	@Cacheable(value = "allContacts") 
	public Page<ContactData> getAll(Pageable pageable) {
	    Page<Contact> contactsPage = repository.findAll(pageable);
	    List<ContactData> contactsData = contactsPage.getContent().stream()
	        .map(contact -> new ContactData(contact.getId(), 
	                                         contact.getClient().getCpf(), 
	                                         contact.getType(), 
	                                         contact.getValue(), 
	                                         contact.getCurrency()))
	        .collect(Collectors.toList()); 
	    return new PageImpl<ContactData>(contactsData, pageable, contactsPage.getTotalElements());
	}
	
	@Cacheable(value = "contactById", key = "#id")
	public ContactData getById(BigInteger id) {
		// Check the contact in DB
		Optional<Contact> optionalContact = repository.findById(id);
		
		if (optionalContact.isEmpty()) {
			return null;
		}
		
		// Get the Contact from DB
		Contact contact = optionalContact.get();
		
		// Return retrieved Data
		return new ContactData(contact.getId(), 
							   contact.getClient().getCpf(), 
							   contact.getType(), 
							   contact.getValue(), 
							   contact.getCurrency());
	}
	
	@CachePut(value = "contact", key = "#id")
	@CacheEvict(value = {"contact", "allContacts"}, key = "#id", allEntries = true)
	public ContactData edit(BigInteger id, Contact contact) {
		// Check if the Client exists
		String cpf = contact.getClient().getCpf();
	    Optional<Client> optionalClient = clients.findByCpf(cpf);
	
	    if (optionalClient.isEmpty()) {
	        return null;
	    }
	    
		// After, if exists, get it and set
		Client client = optionalClient.get();
		 
		// Then create a new edited Contact
		Contact updatedContact = Contact.builder()
				 						.client(client)
				 						.type(contact.getType())
				 						.value(contact.getValue())
				 						.currency(contact.getCurrency())
				 						.build();
		 
		// Set the ID and save
		updatedContact.setId(id);
		Contact updated = repository.save(updatedContact);
		 
		return new ContactData(updated.getId(), 
							   updated.getClient().getCpf(),
							   contact.getType(), 
							   contact.getValue(),
							   contact.getCurrency());
	}
	
	@CacheEvict(value = {"contact", "allContacts"}, key = "#id", allEntries = true)
	public Contact deleteById(BigInteger id) {
		Optional<Contact> optionalContact = repository.findById(id);
		
		if (optionalContact.isPresent()) {
			Contact deletedContact = optionalContact.get();
			repository.delete(deletedContact);
			return deletedContact;
		} else {
			return null;
		}
	}
	
}
