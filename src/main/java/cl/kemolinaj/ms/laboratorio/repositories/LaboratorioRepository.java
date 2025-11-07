package cl.kemolinaj.ms.laboratorio.repositories;

import cl.kemolinaj.ms.laboratorio.entities.LaboratorioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("laboratorioRepository")
public interface LaboratorioRepository extends CrudRepository<LaboratorioEntity, Long> {
}
