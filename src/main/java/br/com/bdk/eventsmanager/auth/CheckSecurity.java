package br.com.bdk.eventsmanager.auth;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface City {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_CIDADE.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_CIDADE.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_CIDADE.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface Company {

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_EMPRESA.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_EMPRESA.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface Country {

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasRole('ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface Course {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_CURSOS.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_CURSOS.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_CURSOS.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_CURSOS.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface Event {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_EVENTO.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_EVENTO.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_EVENTO.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_EVENTO.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface EducationalInstitution {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_INSTITUICAO_EDUCACIONAL.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_INSTITUICAO_EDUCACIONAL.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_INSTITUICAO_EDUCACIONAL.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_INSTITUICAO_EDUCACIONAL.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface Profile {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_PERFIL.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_PERFIL.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_PERFIL.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_PERFIL.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface Service {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_SERVICO.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_SERVICO.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_SERVICO.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_SERVICO.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface State {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_ESTADO.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_ESTADO.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_ESTADO.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface Supplier {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_FORNECEDOR.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_FORNECEDOR.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_FORNECEDOR.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_FORNECEDOR.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }

    @interface User {

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_USUARIO.INSERIR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanCreate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_USUARIO.VISUALIZAR'," +
                "'ROLE_ADMIN')")
        @Target(ElementType.METHOD)
        @interface CanRead {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_USUARIO.EDITAR'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanUpdate {
        }

        @PreAuthorize("hasAnyRole(" +
                "'ROLE_CADASTRO_USUARIO.REMOVER'," +
                "'ROLE_ADMIN')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @interface CanDelete {
        }

    }


}
