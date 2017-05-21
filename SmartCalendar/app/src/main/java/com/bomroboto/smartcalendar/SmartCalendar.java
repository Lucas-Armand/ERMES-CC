package com.bomroboto.smartcalendar;

import android.app.Application;
import android.content.res.Configuration;

import com.bomroboto.smartcalendar.data.SmartCalendarDatabase;
import com.bomroboto.smartcalendar.models.Address;
import com.bomroboto.smartcalendar.models.Appointment;
import com.bomroboto.smartcalendar.models.Business;
import com.bomroboto.smartcalendar.models.Customer;
import com.bomroboto.smartcalendar.models.DayOfWeek;
import com.bomroboto.smartcalendar.models.Employee;
import com.bomroboto.smartcalendar.models.Schedule;
import com.bomroboto.smartcalendar.models.Service;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
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

        FlowManager.getDatabase(SmartCalendarDatabase.class).reset(this);

        populateDatebase();
    }

    private void populateDatebase() {

        List<Business> businesses = SQLite
                .select()
                .from(Business.class).queryList();

        if (businesses.isEmpty()) {
            Business business = new Business("Multi Oral", new LocalTime(8, 00), new LocalTime(20, 00), "Saúde");
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

            ArrayList<Schedule> schedules1 = new ArrayList<>();
            schedules1.add(new Schedule(DayOfWeek.MONDAY, new LocalTime(10, 00), new LocalTime(19, 00), new LocalTime(12, 00),
                    new LocalTime(13, 00), true));
            schedules1.add(new Schedule(DayOfWeek.TUESDAY, new LocalTime(7, 00), new LocalTime(16, 00), new LocalTime(12, 00),
                    new LocalTime(13, 00), true));
            ArrayList<Schedule> schedules2 = new ArrayList<>();
            schedules2.add(new Schedule(DayOfWeek.WEDNESDAY, new LocalTime(7, 00), new LocalTime(16, 00),
                    new LocalTime(12, 00), new LocalTime(13, 00), true));
            schedules2.add(new Schedule(DayOfWeek.THURSDAY, new LocalTime(8, 00), new LocalTime(17, 00), new LocalTime(13, 00),
                    new LocalTime(14, 00), true));
            ArrayList<Schedule> schedules3 = new ArrayList<>();
            schedules3.add(new Schedule(DayOfWeek.FRIDAY, new LocalTime(8, 00), new LocalTime(14, 00), new LocalTime(13, 00),
                    new LocalTime(14, 00), true));

            employees.add(new Employee("Dre", "Guess", schedules1));
            employees.add(new Employee("Dentista", "Graduada", schedules2));
            employees.add(new Employee("Dentista", "Estagiária", schedules3));
            business.setEmployees(employees);

            appointments.add(new Appointment(new LocalDate(2017, Calendar.MAY, 25), new LocalTime(10, 30),
                    business.getServices().get(0), business.getCustomers().get(0), business.getEmployees().get(0),
                    "Aguardando confirmação"));

            appointments.add(new Appointment(new LocalDate(2017, Calendar.MAY, 25), new LocalTime(15, 30),
                    business.getServices().get(1), business.getCustomers().get(1), business.getEmployees().get(1),
                    "Confirmado"));

            appointments.add(new Appointment(new LocalDate(2017, Calendar.MAY, 30), new LocalTime(15, 30),
                    business.getServices().get(2), business.getCustomers().get(2), business.getEmployees().get(2),
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
