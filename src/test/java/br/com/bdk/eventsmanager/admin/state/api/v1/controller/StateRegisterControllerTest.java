package br.com.bdk.eventsmanager.admin.state.api.v1.controller;

import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateCountryModelAssembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.disassembler.StateInputDisassembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateCountryModel;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.input.StateInput;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.service.StateRegisterService;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StateRegisterControllerTest {

    @InjectMocks
    private StateRegisterController controller;

    @Mock
    private StateRegisterService stateRegisterService;

    @Mock
    private StateCountryModelAssembler stateCountryModelAssembler;

    @Mock
    private StateInputDisassembler stateInputDisassembler;


    @Test
    void shouldFindStateById() {
        String externalId = "123-ABCD";
        State state = new State();
        when(stateRegisterService.findByExternalId(externalId)).thenReturn(state);
        when(stateCountryModelAssembler.toModel(state)).thenReturn(StateCountryModel.builder().build());

        var result = controller.findByExternalId(externalId);
        assertNotNull(result);
        inOrder(stateRegisterService, stateCountryModelAssembler);
    }

    @Test
    void shouldCreateState() {
        var input = new StateInput();
        State stateToCreate = new State();
        State stateCreated = new State();
        when(stateInputDisassembler.toEntity(input)).thenReturn(stateToCreate);
        when(stateRegisterService.saveAndValidate(stateToCreate))
                .thenReturn(stateCreated);
        StateCountryModel stateCountryModel = StateCountryModel.builder().build();
        when(stateCountryModelAssembler.toModel(stateCreated)).thenReturn(stateCountryModel);

        var result = controller.create(input);
        assertEquals(stateCountryModel, result);
        inOrder(stateInputDisassembler, stateRegisterService, stateCountryModelAssembler);
    }

    @Test
    void shouldUpdateState() {
        String externalId = "123-ABCD";
        StateInput input = new StateInput();
        State stateToUpdate = new State();
        State stateUpdated = new State();
        StateCountryModel stateCountryModel = StateCountryModel.builder().build();

        when(stateRegisterService.findByExternalId(externalId)).thenReturn(stateToUpdate);
        when(stateRegisterService.saveAndValidate(stateToUpdate)).thenReturn(stateUpdated);
        when(stateCountryModelAssembler.toModel(stateUpdated)).thenReturn(stateCountryModel);

        var result = controller.updateByExternalId(externalId, input);
        assertEquals(stateCountryModel, result);
        inOrder(stateRegisterService, stateInputDisassembler, stateCountryModelAssembler);
        verify(stateInputDisassembler, times(1)).copyToEntity(input, stateToUpdate);
    }

    @Test
    void shouldFindAllStates() {
        String externalId = "123-ABCD";
        var page = Pageable.unpaged();
        Page<State> statePage = Page.empty();
        when(stateRegisterService.findAll(externalId, null, page)).thenReturn(statePage);
        PageModel<StateCountryModel> expectedPageModel = new PageModel<>(Page.empty());
        when(stateCountryModelAssembler.toPageModel(statePage)).thenReturn(expectedPageModel);

        var result = controller.findAll(externalId, null, page);
        assertEquals(expectedPageModel, result);
        inOrder(stateRegisterService, stateCountryModelAssembler);
    }
}
