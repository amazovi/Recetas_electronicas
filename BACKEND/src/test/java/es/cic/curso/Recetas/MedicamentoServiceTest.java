package es.cic.curso.Recetas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.cic.curso.Recetas.dto.MedicamentoDTO;
import es.cic.curso.Recetas.model.Medicamento;
import es.cic.curso.Recetas.repository.MedicamentoRepository;
import es.cic.curso.Recetas.service.MedicamentoService;

public class MedicamentoServiceTest {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private MedicamentoService medicamentoService;

    private Medicamento medicamento;
    private MedicamentoDTO medicamentoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNombre("Paracetamol");
        medicamento.setDescripcion("Antipirético utilizado para reducir fiebre y dolor.");
        medicamento.setDosisRecomendada("500 mg cada 8 horas");
        medicamento.setLaboratorio("Lab D");
        medicamento.setViaAdministracion("Oral");
        medicamento.setContraindicaciones("No exceder la dosis recomendada.");
        medicamento.setPrecio(new BigDecimal("5.99"));

        medicamentoDTO = new MedicamentoDTO();
        medicamentoDTO.setId(1L);
        medicamentoDTO.setNombre("Paracetamol");
        medicamentoDTO.setDescripcion("Antipirético utilizado para reducir fiebre y dolor.");
        medicamentoDTO.setDosisRecomendada("500 mg cada 8 horas");
        medicamentoDTO.setLaboratorio("Lab D");
        medicamentoDTO.setViaAdministracion("Oral");
        medicamentoDTO.setContraindicaciones("No exceder la dosis recomendada.");
        medicamentoDTO.setPrecio(new BigDecimal("5.99"));
    }

    @Test
    public void testFindAll() {
        List<Medicamento> medicamentos = Arrays.asList(medicamento);
        when(medicamentoRepository.findAll()).thenReturn(medicamentos);

        List<MedicamentoDTO> result = medicamentoService.findAll();
        assertEquals(1, result.size());
        assertEquals("Paracetamol", result.get(0).getNombre());
    }

    @Test
    public void testFindById() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));

        Optional<Medicamento> result = medicamentoService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Paracetamol", result.get().getNombre());
    }

    @Test
    public void testSave() {
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamento);

        Medicamento result = medicamentoService.save(medicamento);
        assertNotNull(result);
        assertEquals("Paracetamol", result.getNombre());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(medicamentoRepository).deleteById(1L);

        medicamentoService.deleteById(1L);
        verify(medicamentoRepository, times(1)).deleteById(1L);
    }
}