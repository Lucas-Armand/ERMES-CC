package com.bomroboto.smartcalendar;

import android.app.Application;
import android.content.res.Configuration;

import com.bomroboto.smartcalendar.models.*;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.joda.time.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei Benincasa on 14/02/2017.
 * andrei.benincasa@gmail.com
 */
public class SmartCalendar extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());

        // Set verbose logging
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        populateDatebase();
    }

    private void populateDatebase() {

        List<Business> businesses = SQLite
                .select()
                .from(Business.class).queryList();

        if (businesses.isEmpty()) {
            Business business = new Business("Multi Oral", LocalTime.parse("8:00"), LocalTime.parse("20:00"), "Saúde");
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
            schedules1.add(new Schedule(DayOfWeek.MONDAY, LocalTime.parse("10:00"), LocalTime.parse("19:00"), LocalTime.parse("12:00"),
                    LocalTime.parse("13:00"), true));
            schedules1.add(new Schedule(DayOfWeek.TUESDAY, LocalTime.parse("7:00"), LocalTime.parse("16:00"), LocalTime.parse("12:00"),
                    LocalTime.parse("13:00"), true));
            List<Schedule> schedules2 = new ArrayList<>();
            schedules2.add(new Schedule(DayOfWeek.WEDNESDAY, LocalTime.parse("7:00"), LocalTime.parse("16:00"),
                    LocalTime.parse("12:00"), LocalTime.parse("13:00"), true));
            schedules2.add(new Schedule(DayOfWeek.THURSDAY, LocalTime.parse("8:00"), LocalTime.parse("17:00"), LocalTime.parse("13:00"),
                    LocalTime.parse("14:00"), true));
            List<Schedule> schedules3 = new ArrayList<>();
            schedules3.add(new Schedule(DayOfWeek.FRIDAY, LocalTime.parse("8:00"), LocalTime.parse("14:00"), LocalTime.parse("13:00"),
                    LocalTime.parse("14:00"), true));

            employees.add(new Employee("Dre", "Guess", schedules1));
            employees.add(new Employee("Dentista", "Graduada", schedules2));
            employees.add(new Employee("Dentista", "Estagiária", schedules3));
            business.setEmployees(employees);

            DateTime dateTime = new DateTime();

            appointments.add(new Appointment(LocalDate.parse(dateTime.getYear() + "-" + Months.FIVE + "-" + 25), LocalTime.parse("10:30"),
                    business.getServices().get(0), business.getCustomers().get(0), business.getEmployees().get(0),
                    "Aguardando confirmação"));

            appointments.add(new Appointment(LocalDate.parse(dateTime.getYear() + "-" + Months.FIVE + "-" + 30), LocalTime.parse("15:30"),
                    business.getServices().get(1), business.getCustomers().get(1), business.getEmployees().get(1),
                    "Confirmado"));
            business.setAppointments(appointments);

            business.save();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
