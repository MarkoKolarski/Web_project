package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.RecenzijaRepository;
import com.example.demo.repository.ZanrRepository;
import com.example.demo.repository.PolicaRepository;
import com.example.demo.repository.StavkaPoliceRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

//import static com.example.demo.model.Uloga.AUTOR;

/*
   @SpringBootApplication anotacija nastala je od @EnableAutoConfiguration anotacije koja
   upravlja konfiguracijom aplikacije.
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	/* Da bismo testirali repozitorijum, direktno smo pozvali u glavnoj klasi metode,
	   inace bi pozivi bili u npr. nekom od servisa.
	 */
	@Autowired
	private StavkaPoliceRepository stavkaPoliceRepository;

	@Autowired
	private PolicaRepository policaRepository;

	@Autowired
	private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;
	@Autowired
	private ZanrRepository zanrRepository;
	@Autowired
	private RecenzijaRepository recenzijaRepository;


	@Autowired
	private KorisnikRepository korisnikRepository;

	@Autowired
	private KnjigaRepository knjigaRepository;

	@Override
	public void run(String... args) {

//		// kreiramo novi objekat klase Employee
//		Korisnik korisnik = new Korisnik();
//		korisnik.setIme("Marko");
//		korisnik.setPrezime("Rosić");
//		korisnik.setOpis("najjaci lik");
//		korisnik.setLozinka("Roske_boss");
//		//korisnik.setDatumRodjenja("2023-04-15 14:30:00");
//		korisnik.setKorisnickoIme("Roske");
//		korisnik.setMejlAdresa("thespykid2002@gmail.com");
//		korisnik.setProfilnaSlika("roske_formula");
//		korisnik.setUloga(Uloga.CITALAC);

		// čuvamo objekat u bazi
//		this.korisnikRepository.save(korisnik);

		List<Korisnik> korisnici = this.korisnikRepository.findAll();
		//List<Korisnik> korisnici = this.korisnikRepository.findAllByPrezime("Kolarski");
		List<Knjiga> knjige = this.knjigaRepository.findAll();
		List<Recenzija> recenzije = this.recenzijaRepository.findAll();
		List<Zanr> zanrovi = this.zanrRepository.findAll();
		List<ZahtevZaAktivacijuNalogaAutora> zahtevi = this.zahtevZaAktivacijuNalogaAutoraRepository.findAll();
		List<StavkaPolice> stavkaPolica = this.stavkaPoliceRepository.findAll();
		List<Polica> police = this.policaRepository.findAll();

		for (Polica e : police){
			System.out.println(e);
		}

		for (StavkaPolice e : stavkaPolica){
			System.out.println(e);
		}

		for (ZahtevZaAktivacijuNalogaAutora e : zahtevi){
			System.out.println(e);
		}


		for (Zanr e : zanrovi){
			System.out.println(e);
		}

		for (Recenzija e : recenzije){
			System.out.println(e);
		}

		for (Knjiga e : knjige){
			System.out.println(e);
		}

		for (Korisnik e : korisnici){
			System.out.println(e);
		}


	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
