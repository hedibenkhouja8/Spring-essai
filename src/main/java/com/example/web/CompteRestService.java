package com.example.web;
 
import java.util.Date;
import java.util.Optional;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.CompteRepository;
import com.example.entities.Compte;
import com.sun.research.ws.wadl.Response;
 
 
@Component
@Path("/Banque")
public class CompteRestService {
    @Autowired
    private CompteRepository compteRepository  ;
    @Autowired



    @GET
    @Path("/comptes")
    @Produces(MediaType.APPLICATION_JSON)

    public java.util.List<Compte> getComptes(){
        return compteRepository.findAll();
    }

    @GET
    @Path("/comptes/{code}")
    @Produces(MediaType.APPLICATION_JSON)

    public Optional<Compte> getCompte(@PathParam(value = "code") Long code){
        return compteRepository.findById(code);
    }
 
    
    @POST
    @Path("/comptes/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Compte addCompte(@RequestBody Compte newCompte) {        
        return  compteRepository.save(newCompte);
    }

    
    @PUT
    @Path("/compte/update/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Compte updateCompte(@PathParam(value = "code") Long code ,@RequestParam Compte Ucompte) {       
    	//Optional compte = compteRepository.findById(code);

        Optional<Compte> Oldcompte =Optional.of(compteRepository.findById(Ucompte.getCode())).orElseThrow(RuntimeException::new);

        return  compteRepository.save(Ucompte);
    }
 
  
    @DELETE
    @Path("/compte/delete/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam(value = "code") Long code) {
        compteRepository.deleteById(code);
    }

    @GET
    @Path("/comptes/conversion/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Double conversion(@PathParam(value = "code")Long code ,Compte compte) {
        double eur=0.305331;
        double s =compteRepository.findById(code).get().getSolde();
        double conversion=s*eur;

        return conversion;
    }
 
}