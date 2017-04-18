package com.ermes.api;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.*;
import com.ermes.api.services.UserService;

@RestController
@SpringBootApplication
public class App
{
	@RequestMapping("/*")
	public String index()
	{
		return "ERMES API";
	}

	public static void main(String[] args)
	{
		ApplicationContext context = SpringApplication.run(App.class, args);

		UserService userService = context.getBean(UserService.class);

		createDefaultUsers(userService);
	}

	static void createDefaultUsers(UserService userService)
	{
		if (userService.getUsers().isEmpty())
		{
			User admin = new User();
			admin.setEmail("admin@email.com");
			admin.setPassword("12345");
			admin.setBusiness(new Business("FICTIONAL", LocalTime.of(8, 00), LocalTime.of(16, 00), "FICTIONAL"));
			userService.saveAdmin(admin);

			User user = new User();
			user.setEmail("user@email.com");
			user.setPassword("12345");
			user.setBusiness(new Business("FICTIONAL", LocalTime.of(8, 00), LocalTime.of(16, 00), "FICTIONAL"));
			userService.saveUser(user);

			populateDatabase(userService);
		}
	}

	static void populateDatabase(UserService userService)
	{
		User multioral = new User();
		multioral.setEmail("multi.oral@email.com");
		multioral.setPassword("12345");

		Business business = new Business("Multi Oral", LocalTime.of(8, 00), LocalTime.of(20, 00), "Saúde");
		List<Service> services = new ArrayList<>();
		List<Customer> customers = new ArrayList<>();
		List<Employee> employees = new ArrayList<>();
		List<Appointment> appointments = new ArrayList<>();

		services.add(new Service("Consulta", "Análise dos dentes e tecidos moles.", 30, 50.00));
		services.add(new Service("Clareamento",
				"Procedimento simples, não invasivo, que muda a cor dos dentes naturais.", 120, 100.00));
		services.add(new Service("Limpeza", "Limpeza dos dentes para remoção da placa bacteriana.", 60, 200.00));
		business.setServices(services);

		customers.add(new Customer("Fulano", "Silva", "(21) 123-456-789", "fulano@email.com",
				new Address("Rua do Bairro", 666, "Bairro", "Cidade")));
		customers.add(new Customer("Beltrano", "Souza", "(21) 234-567-890", "beltrano@email.com",
				new Address("Avenida do Bairro", 666, "Bairro", "Cidade")));
		customers.add(new Customer("Ciclano", "Santos", "(21) 345-678-901", "fulano@email.com",
				new Address("Travessa do Outro Bairro", 666, "Outro Bairro", "Cidade")));
		business.setCustomers(customers);

		List<Schedule> schedules1 = new ArrayList<>();
		schedules1.add(new Schedule(DayOfWeek.MONDAY, LocalTime.of(10, 00), LocalTime.of(19, 00), LocalTime.of(12, 00),
				LocalTime.of(13, 00), true));
		schedules1.add(new Schedule(DayOfWeek.TUESDAY, LocalTime.of(07, 00), LocalTime.of(16, 00), LocalTime.of(12, 00),
				LocalTime.of(13, 00), true));
		List<Schedule> schedules2 = new ArrayList<>();
		schedules2.add(new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(07, 00), LocalTime.of(16, 00),
				LocalTime.of(12, 00), LocalTime.of(13, 00), true));
		schedules2.add(new Schedule(DayOfWeek.THURSDAY, LocalTime.of(8, 00), LocalTime.of(17, 00), LocalTime.of(13, 00),
				LocalTime.of(14, 00), true));
		List<Schedule> schedules3 = new ArrayList<>();
		schedules3.add(new Schedule(DayOfWeek.FRIDAY, LocalTime.of(8, 00), LocalTime.of(14, 00), LocalTime.of(13, 00),
				LocalTime.of(14, 00), true));

		employees.add(new Employee("Dre", "Guess", schedules1));
		employees.add(new Employee("Dentista", "Graduada", schedules2));
		employees.add(new Employee("Dentista", "Estagiária", schedules3));
		business.setEmployees(employees);

		appointments.add(new Appointment(LocalDate.of(Year.now().getValue(), Month.APRIL, 10), LocalTime.of(10, 30),
				business.getServices().get(0), business.getCustomers().get(0), business.getEmployees().get(0),
				"Aguardando confirmação."));
		appointments.add(new Appointment(LocalDate.of(Year.now().getValue(), Month.APRIL, 16), LocalTime.of(15, 30),
				business.getServices().get(1), business.getCustomers().get(1), business.getEmployees().get(1),
				"Confirmado"));
		business.setAppointments(appointments);

		multioral.setBusiness(business);
		userService.saveUser(multioral);

		User oficinadoc = new User();
		oficinadoc.setEmail("oficina.doc@email.com");
		oficinadoc.setPassword("12345");

		services = new ArrayList<>();
		customers = new ArrayList<>();
		employees = new ArrayList<>();
		appointments = new ArrayList<>();

		business = new Business("Oficina do Doc", LocalTime.of(8, 00), LocalTime.of(20, 00), "Automotiva");

		services.add(new Service("Lanternagem", "Reforma de lataria de carro.", 60, 150.00));
		services.add(new Service("Pintura", "Colorir a lataria do carro.", 180, 300.00));
		business.setServices(services);

		customers.add(new Customer("Harley", "Davidson", "(21) 123-456-789", "davidson@email.com",
				new Address("Rua do Bairro", 666, "Bairro", "Cidade")));
		customers.add(new Customer("Ayrton", "Senna", "(21) 234-567-890", "senna@email.com",
				new Address("Avenida do Bairro", 666, "Bairro", "Cidade")));
		business.setCustomers(customers);

		schedules1 = new ArrayList<>();
		schedules1.add(new Schedule(DayOfWeek.MONDAY, LocalTime.of(10, 00), LocalTime.of(19, 00), LocalTime.of(12, 00),
				LocalTime.of(13, 00), true));
		schedules1.add(new Schedule(DayOfWeek.TUESDAY, LocalTime.of(07, 00), LocalTime.of(16, 00), LocalTime.of(12, 00),
				LocalTime.of(13, 00), true));
		schedules1.add(new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(07, 00), LocalTime.of(16, 00),
				LocalTime.of(12, 00), LocalTime.of(13, 00), true));
		schedules2 = new ArrayList<>();
		schedules2.add(new Schedule(DayOfWeek.THURSDAY, LocalTime.of(8, 00), LocalTime.of(17, 00), LocalTime.of(13, 00),
				LocalTime.of(14, 00), true));
		schedules2.add(new Schedule(DayOfWeek.FRIDAY, LocalTime.of(8, 00), LocalTime.of(14, 00), LocalTime.of(13, 00),
				LocalTime.of(14, 00), true));

		employees.add(new Employee("Emmett", "Brown", schedules1));
		employees.add(new Employee("Marty", "McFly", schedules2));
		business.setEmployees(employees);

		appointments.add(new Appointment(LocalDate.of(Year.now().getValue(), Month.APRIL, 13), LocalTime.of(10, 30),
				business.getServices().get(0), business.getCustomers().get(0), business.getEmployees().get(0),
				"Aguardando confirmação."));
		appointments.add(new Appointment(LocalDate.of(Year.now().getValue(), Month.APRIL, 20), LocalTime.of(15, 30),
				business.getServices().get(1), business.getCustomers().get(1), business.getEmployees().get(1),
				"Confirmado"));
		business.setAppointments(appointments);

		oficinadoc.setBusiness(business);
		userService.saveUser(oficinadoc);
	}
}
