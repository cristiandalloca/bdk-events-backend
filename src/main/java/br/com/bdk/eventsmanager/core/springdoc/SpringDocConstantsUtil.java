package br.com.bdk.eventsmanager.core.springdoc;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpringDocConstantsUtil {

    public static final String API_RESPONSE_RESOURCE_NOT_FOUND_DESCRIPTION = "Resource not found";
    public static final String API_RESPONSE_BAD_REQUEST_DESCRIPTION = "Mandatory parameter blank or null";
    public static final String API_RESPONSE_INTERNAL_ERROR_DESCRIPTION = "Internal server error";
    public static final String API_RESPONSE_UNAUTHORIZED_DESCRIPTION = "Unauthorized access";

    @UtilityClass
    public static class Example {
        public static final String IDENTIFIER = "dfd197a2-d345-4834-90dc-57867c0e2fa0";
        public static final String EMAIL = "cristiandalloca@gmail.com";
        public static final String PHONE_NUMBER = "18981832227";
        public static final String PRIVILEGE_NAME = "CADASTRO_EMPRESA.EDITAR";
        public static final String PRIVILEGE_DESCRIPTION = "Permite editar registros no menu de cadastro de empresas";
        public static final String PROFILE_NAME = "Vendedor externo";
        public static final String COUNTRY_NAME = "Brasil";
        public static final String COUNTRY_ACRONYM = "BRA";
        public static final String ADDRESS_POSTAL_CODE = "16018220";
        public static final String ADDRESS_STREET = "Travessa Nildo Neponoceno Fernandes";
        public static final String ADDRESS_STREET_NUMBER = "2456";
        public static final String ADDRESS_NEIGHBORHOOD = "Jardim Nova Iorque";
        public static final String ADDRESS_COMPLEMENT = "Apto 123, bloco C";
        public static final String CITY_NAME = "Florianópolis";
        public static final String STATE_NAME = "Santa Catarina";
        public static final String STATE_ACRONYM = "SC";
        public static final String COMPANY_NAME = "BDK Gestão Eventos LTDA";
        public static final String COMPANY_BUSINESS_NAME = "BDK";
        public static final String COMPANY_DOCUMENT = "82683633000106";
        public static final String COMPANY_STATE_REGISTRATION_NUMBER = "308624707466";
        public static final String COMPANY_CITY_REGISTRATION_NUMBER = "12473";
        public static final String BOOLEAN = "true";
        public static final String HTML_TEXT = """
                <html>
                <body>
                <p><b>This text is bold</b></p>
                <p><i>This text is italic</i></p>
                <p>This is<sub> subscript</sub> and <sup>superscript</sup></p>
                </body>
                </html>
                """;
        public static final String IDENTIFIER_ARRAY = "[\"dfd197a2-d345-4834-90dc-57867c0e2fa0\", \"dfd778a2-d345-4834-90dc-57867c0e2fa0\"]";
        public static final String PRIVILEGES_NAME_ARRAY = "[\"ROLE_CADASTRAR_CURSOS.VISUALIZAR\", \"ROLE_CADASTRAR_CIDADE.EDITAR\"]";
        public static final String PASSWORD = "Mud@r202405";
        public static final String USER_NAME = "Cristian Berti";
        public static final String USER_LOGIN = "cristian.berti";
        public static final String EVENT_NAME = "Colação de grau";
        public static final String EVENT_TYPES = "[\"GRADUATION\", \"WEDDING\"]";
        public static final String SERVICE_NAME = "Telão com fotos";
        public static final String SERVICE_COST_TYPE = "FIXED";
        public static final String DECIMAL = "144.89";
        public static final String PERCENTAGE = "52.41";
        public static final String EVENT_TYPE = "WEDDING";
        public static final String EVENT_TYPE_DESCRIPTION = "Casamento";
        public static final String ARCHIVE_URI = "gs://directory/archive.extension";
        public static final String ARCHIVE_URL = "https://google.com.br/arquivo.extensao";
        public static final String EVENT_SERVICE_MEASUREMENT_TYPE_IDENTIFIER = "UNIQUE";
        public static final String EVENT_SERVICE_MEASUREMENT_TYPE_DESCRIPTION = "Único";
        public static final String INTEGER = "5";
        public static final String INVITATION_TYPE_NAME = "Jantar";
        public static final String EDUCATIONAL_INSTITUTION_NAME = "Nome da instituição de ensino";
        public static final String EDUCATIONAL_INSTITUTION_TYPE = "HIGHER_EDUCATION";
        public static final String EDUCATIONAL_INSTITUTION_TYPE_DESCRIPTION = "Ensino superior";
        public static final String COURSE_NAME = "Engenharia da Computação";
        public static final String SUPPLIER_NAME = "Dell Inc.";
        public static final String SUPPLIER_PLACE_NAME = "Quarta Avenida";
        public static final String TOKEN = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJjcmlzdGlhbmJlcnRpIiwiaWF0IjoxNzI1NDk1MTE0LCJleHAiOjE3MjU1ODE1MTR9.FUBxtN1Bn7KJcHbOFiUEHIvqrE3EpLntnKLSjSpAvU6v1Bll_7uQTKJNSVoq0U6M";
        public static final String TOKEN_TYPE = "bearer";
        public static final String TOKEN_EXPIRES_IN = "447845446";
        public static final String USER_FIRST_LETTER_FIRST_NAME_LAST_NAME = "CB";
    }

    @UtilityClass
    public static class Description {
        public static final String PROFILE_IDENTIFIER = "Identificador do perfil";
        public static final String PROFILE_NAME = "Nome do perfil";
        public static final String PROFILE_PRIVILEGES_IDENTIFIER = "Identificadores dos privilégios que o perfil possui";
        public static final String PRIVILEGE_IDENTIFIER = "Identificador do privilégio";
        public static final String PRIVILEGE_NAME = "Nome do privilégio";
        public static final String PRIVILEGE_DESCRIPTION = "Descrição do privilégio";
        public static final String CITY_IDENTIFIER = "Identificador da cidade";
        public static final String CITY_NAME = "Nome da cidade";
        public static final String STATE_IDENTIFIER = "Identificador do estado";
        public static final String STATE_NAME = "Nome do estado";
        public static final String STATE_ACRONYM = "Sigla de abreviação do estado";
        public static final String COMPANY_IDENTIFIER = "Identificador da empresa";
        public static final String COMPANY_NAME = "Razão social";
        public static final String COMPANY_BUSINESS_NAME = "Nome fantasia";
        public static final String COMPANY_DOCUMENT = "CNPJ ou CPF, sem pontuações";
        public static final String COMPANY_STATE_REGISTRATION_NUMBER = "Inscrição Estadual (IE), sem pontuação";
        public static final String COMPANY_CITY_REGISTRATION_NUMBER = "Inscrição Municipal (IM), sem pontuação";
        public static final String COMPANY_EMAIL = "Endereço de email";
        public static final String COMPANY_PHONE_NUMBER = "Número do telefone ou celular com DDD, sem formatação";
        public static final String ADDRESS_POSTAL_CODE = "CEP do endereço, sem formatação";
        public static final String ADDRESS_STREET = "Logradouro (ex: Rua, Travessa, Avenida...)";
        public static final String ADDRESS_STREET_NUMBER = "Número do logradouro";
        public static final String ADDRESS_NEIGHBORHOOD = "Bairro";
        public static final String ADDRESS_COMPLEMENT = "Complemento do endereço";
        public static final String COMPANY_ACTIVE = "Boleano que indica se a empresa está ativa ou não";
        public static final String COMPANY_LOGO = "Logotipo da empresa (máximo 5MB, apenas JPG/JPEG ou PNG";
        public static final String COMPANY_SIGNATURE = "Assinatura da empresa (máximo 5MB, apenas JPG/JPEG ou PNG)";
        public static final String COMPANY_INITIAL_PARAGRAPH_CONTRACT = "Parágrafo inicial do contrato, pode conter informações de formatação HTML";
        public static final String COUNTRY_IDENTIFIER = "Identificador do país";
        public static final String COUNTRY_NAME = "Nome do país";
        public static final String COUNTRY_ACRONYM = "Sigla abreviação do país";
        public static final String NUMBER_PAGE = "Número da página";
        public static final String PAGE_SIZE = "Quantidade de registros da página";
        public static final String USER_ACTUAL_PASSWORD = "Senha atual";
        public static final String USER_NEW_PASSWORD = "Nova senha";
        public static final String USER_NAME = "Nome do usuário";
        public static final String USER_EMAIL = "E-mail do usuário";
        public static final String USER_LOGIN = "Login do usuário";
        public static final String USER_ACTIVE = "Indica se o usuário está ativo ou não";
        public static final String USER_IDENTIFIER = "Identificador do usuário";
        public static final String EVENT_IDENTIFIER = "Identificador do evento";
        public static final String EVENT_NAME = "Nome do evento";
        public static final String EVENT_ACTIVE = "Indica se o evento está ativo ou não";
        public static final String EVENT_TYPE = "Tipo do evento";
        public static final String SERVICE_IDENTIFIER = "Identificador do serviço";
        public static final String SERVICE_NAME = "Nome do serviço";
        public static final String SERVICE_COST_TYPE = "Tipo de custo do serviço";
        public static final String SERVICE_APPLY_BDI = "Indica se o serviço incide no BDI ou não";
        public static final String SERVICE_ACTIVE = "Indica se o serviço está ativo ou não";
        public static final String SERVICE_DESCRIPTION = "Descrição do serviço, pode conter informações de formatação HTML";
        public static final String EVENT_TYPE_DESCRIPTION = "Descrição do tipo de evento";
        public static final String EVENT_SERVICE_IDENTIFIER = "Identificador do vinculo entre serviço e evento";
        public static final String EVENT_SERVICE_PRICE = "Preço do serviço vinculado ao evento";
        public static final String EVENT_SERVICE_DISPLAY_IN_PROPOSAL = "Indica se o serviço vinculado ao evento será exibido na proposta";
        public static final String EVENT_SERVICE_DISPLAY_PRICE_IN_PROPOSAL = "Indica se o preço do serviço vinculado ao evento será exibido na proposta";
        public static final String EVENT_SERVICE_ALLOW_CHANGE_QUANTITY = "Indica se a quantidade informada do serviço vinculado ao evento poderá ser alterada na elaboração da proposta";
        public static final String EVENT_SERVICE_ALLOW_CHANGE_PRICE = "Indica se o preço informado no serviço vinculado ao evento poderá ser alterado na elaboração da proposta";
        public static final String EVENT_SERVICE_MEASUREMENT_TYPE_IDENTIFIER = "Indica o tipo da quantidade do serviço vinculado ao evento";
        public static final String EVENT_SERVICE_MEASUREMENT_TYPE_DESCRIPTION = "Descrição do tipo da quantidade do serviço vinculado ao evento";
        public static final String EVENT_SERVICE_QUANTITY = "Quantidade do serviço vinculado ao evento";
        public static final String INVITATION_TYPE_IDENTIFIER = "Identificador do tipo de convite";
        public static final String INVITATION_TYPE_NAME = "Nome do tipo de convite";
        public static final String EVENT_SERVICE_MINIMUM_PRICE = "Preço mínimo do serviço vinculado ao evento";
        public static final String COMPANY_INVITATION_TYPE_IDENTIFIER = "Identificador do tipo de convite relacionado a empresa";
        public static final String SERVICE_PHOTO_IDENTIFIER = "Identificador da foto do serviço";
        public static final String SERVICE_PHOTO_NUMBER = "Número da foto do serviço";
        public static final String SERVICE_PHOTO_URL = "Link da foto do serviço";
        public static final String EDUCATIONAL_INSTITUTION_NAME = "Nome da instituição de ensino";
        public static final String EDUCATIONAL_INSTITUTION_ACTIVE = "Indica se a instituição de ensino está ativa ou não";
        public static final String EDUCATIONAL_INSTITUTION_TYPE = "Tipo da instituição de ensino";
        public static final String EDUCATIONAL_INSTITUTION_IDENTIFIER = "Identificador da instituição de ensino";
        public static final String EDUCATIONAL_INSTITUTION_TYPE_DESCRIPTION = "Descrição do tipo da instituição de ensino";
        public static final String COURSE_IDENTIFIER = "Identificador do curso";
        public static final String COURSE_NAME = "Nome do curso";
        public static final String COURSE_ACTIVE = "Indica se o curso está ativo ou não";
        public static final String COMPANY_BILL_ISSUANCE_FEE_VALUE = "Taxa emissão de boleto (R$)";
        public static final String COMPANY_CREDIT_CARD_FEE_PERCENTAGE = "Taxa cartão de crédito á vista (%)";
        public static final String COMPANY_BDI_PERCENTAGE = "BDI (%)";
        public static final String COMPANY_MAX_PERCENTAGE_SELLER_COMMISSION = "Comissão máxima do vendedor (%)";
        public static final String SERVICE_APPLY_SELLER_COMMISSION = "Indica se o serviço incide na comissão do vendedor ou não";
        public static final String SUPPLIER_NAME = "Nome do fornecedor";
        public static final String SUPPLIER_ACTIVE = "Indica se o fornecedor está ativo ou não";
        public static final String SUPPLIER_HAS_EVENTS_PLACES = "Indica se o fornecedor possui espaços para eventos ou não";
        public static final String SUPPLIER_IDENTIFIER = "Identificador do fornecedor";
        public static final String SUPPLIER_PHONE_NUMBER = "Telefone do fornecedor";
        public static final String SUPPLIER_CELL_PHONE_NUMBER = "Celular do fornecedor";
        public static final String SUPPLIER_EMAIL = "Email do fornecedor";
        public static final String SUPPLIER_PLACE_NAME = "Nome do local do fornecedor";
        public static final String SUPPLIER_PLACE_ACTIVE = "Indica se o local do fornecedor está disponível";
        public static final String SUPPLIER_PLACE_CAPACITY = "Capacidade de pessoas do local do fornecedor (maior que)";
        public static final String SUPPLIER_PLACE_DESCRIPTION = "Descrição do local do fornecedor (pode conter informações em HTML)";
        public static final String SUPPLIER_PLACE_IDENTIFIER = "Identificador do local do fornecedor";
        public static final String SERVICE_SUPPLIER_IDENTIFIER = "Identificador do fornecedor no serviço";
        public static final String USERNAME = "Login ou email do usuário";
        public static final String PASSWORD = "Senha de acesso do usuário";
        public static final String USER_UPDATE_PASSWORD_NEXT_LOGIN = "Indica se o usuário deve realizar a alteração de senha na próxima vez que autenticar-se";
        public static final String USER_FIRST_LETTER_FIRST_NAME_LAST_NAME = "Primeira letra do primeiro nome e primeira letra do sobrenome";
    }


}
