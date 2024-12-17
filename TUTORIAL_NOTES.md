
# TUTORIAL_NOTES.md

## Domain-Driven Design (DDD) Grundlagen

### Was ist eine Domain?
Ein fachlicher Geschäftsbereich mit eigenen:
* Regeln & Prozessen
* Fachbegriffen
* Verhalten
* Beziehungen zu anderen Domains

Beispiel Airbnb Booking-Domain:
* Nicht nur Datenbankstruktur
* Sondern auch:
 * Geschäftsregeln (keine Überbuchungen)
 * Prozesse (Buchungsablauf)
 * Validierungen (min/max Aufenthaltsdauer)
 * Berechnungen (Preise, Verfügbarkeit)
 * Fachsprache (Gast, Host, Buchung)

Die Datenbankstruktur ist nur ein kleiner Teil der Domain.

## Tutorial Schritte Backend

**Backend Config**
0. compose.yaml und resources (db, application.yml und application-dev.yml und application-prod.yml) einrichten


📦 **MODEL LAYER (User Domain)**
1. user/domain => Authority.java erstellen
  * Berechtigungen/Rollen (z.B. ADMIN, USER)
  * Basis für Zugriffsrechte


2. user/domain => User.java erstellen
  * Benutzerinformationen (Name, Email, etc.)
  * Verknüpfung mit Berechtigungen (ManyToMany)


3. sharedkernel/domain => AbstractAuditingEntity.java erstellen
  * Basis für Auditing-Funktionalität
  * Automatische Zeitstempel-Erfassung
  * Gemeinsame Basis für alle Entities


🗄️ **REPOSITORY LAYER**
4. user/repository => UserRepository.java erstellen
  * JPA Repository für Datenbankzugriffe
  * Definiert Methoden für User-Suche
  * Erbt Standard-CRUD-Operationen


🔄 **SERVICE LAYER** (Mapper als Teil der Service-Logik)
5. user/mapper => UserMapper.java erstellen
  * Interface für Object-Mapping
  * Wird Entity in DTO und zurück konvertieren
  * Nutzt MapStruct für automatische Implementierung


⚙️ **INFRASTRUCTURE LAYER**
6. infrastructure/config => DatabaseConfiguration.java erstellen
 * Spring-Konfiguration für Datenbankzugriff
 * Aktiviert JPA-Repositories
 * Ermöglicht Transaktionen
 * Schaltet Auditing-Funktionalität ein
 * Bildet technische Basis für Model, Repository und Service Layer


📦 **MODEL LAYER (Listing Domain)**
7. listing/domain => BookingCategory.java erstellen
   * Enum für Unterkunftskategorien
   * Definiert verfügbare Listing-Typen


8. listing/domain => Listing.java erstellen
   * Kerndaten einer Unterkunft
   * Verknüpfung mit Bildern und Vermieter
   * Erbt Audit-Funktionalität


9. listing/domain => ListingPicture.java erstellen
   * Bildverwaltung für Listings
   * Binary-Datenspeicherung
   * Cover-Bild Markierung


🔄 **SERVICE LAYER (Listing Domain)**
10. listing/mapper => ListingMapper.java & ListingPictureMapper.java erstellen
    * Mapper-Interfaces für DTO-Konvertierung
    * Vorbereitung für Service-Schicht


🗄️ **REPOSITORY LAYER (Listing Domain)**
11. listing/repository => ListingRepository.java & ListingPictureRepository.java erstellen
    * JPA Repositories für Datenbankzugriffe
    * Standard CRUD-Operationen


⚙️ **INFRASTRUCTURE LAYER Update**
12. infrastructure/config => DatabaseConfiguration.java erweitern
    * Repository-Package für Listing-Domain registriert
    * Ermöglicht Datenbankzugriffe für Listing-Funktionalität


📦 **MODEL LAYER (Booking Domain)**
13. booking/domain => Booking.java erstellen
    * Buchungsdaten (Zeitraum, Preis, Reisende)
    * Verknüpfungen zu User und Listing
    * Erbt Audit-Funktionalität


🔄 **SERVICE LAYER (Booking Domain)**
14. booking/mapper => BookingMapper.java erstellen
    * Mapper-Interface für DTO-Konvertierung
    * Vorbereitung für Service-Schicht


🗄️ **REPOSITORY LAYER (Booking Domain)**
15. booking/repository => BookingRepository.java erstellen
    * JPA Repository für Buchungszugriffe
    * Standard CRUD-Operationen


⚙️ **INFRASTRUCTURE LAYER Update**
16. infrastructure/config => DatabaseConfiguration.java erweitern
    * Repository-Package für Booking-Domain registriert
    * Ermöglicht Datenbankzugriffe für Booking-Funktionalität


🔐 **AUTH0 SETUP (Auth0 Dashboard einstellungen)**
### Authentication & Authorization Konfiguration
1. Auth0 Account & Application
   * Account erstellen/einloggen (development tenant)
   * "Create Application" -> "Airbnb clone"
   * Typ: "Regular Web Applications"

2. Application Settings
   * Allowed Callback URLs konfigurieren:
     ```
     http://localhost:8080/login/oauth2/code/okta,
     http://localhost:4200/login/oauth2/code/okta
     ```
   * Allowed Logout URLs erlauben:
     ```
     http://localhost:8080,
     http://localhost:4200
     ```
   * Allowed Origins (CORS) erlauben:
     ```
     http://localhost:8080,
     http://localhost:4200
     ```
   * Hinweis: Diese Einstellungen ermöglichen sichere Authentifizierung zwischen Frontend und Backend.

3. API Konfiguration
   * Unter "APIs" -> "Airbnb-clone" -> "Settings"
   * Authorized Toggle aktivieren
   * Permissions konfigurieren:
     ```
     read:client_grants
     create:client_grants
     delete:client_grants
     update:client_grants
     read:users
     update:users
     delete:users
     create:users
     read:users_app_metadata
     update:users_app_metadata
     create:users_app_metadata
     ```
   * Diese Berechtigungen ermöglichen Benutzerverwaltung und Client-Grant-Kontrolle

4. Rollen-Management
   * Unter "User Management" -> "Roles"
   * System-Rollen erstellen:
     - ROLE_ADMIN: Administrator-Zugriff
     - ROLE_LANDLORD: Vermieter-Rolle
     - ROLE_TENANT: Mieter-Rolle
   * Diese Rollen entsprechen den Authority-Einträgen in unserer Datenbank

5. Branding-Konfiguration
   * Universal Login anpassen:
     - Company Logo: Airbnb Logo (via Wikimedia)
     - Primary Color: #F24b5b (Airbnb-Rot)
     - "Customization Options" => Button Border Radius: 8px
   * Verbessert User Experience beim Login
   * Konsistentes Branding mit Hauptanwendung

6. Post-Login Trigger erstellen und konfigurieren
  * Unter "Actions" -> "Triggers" -> "Post Login"
  * Neue Action "Add default role" erstellen
  * Runtime: Node 18 auswählen

7. Dependencies einrichten
  * Im Action-Editor unter "Settings"
  * Package "auth0" (Version 4.13.0+) hinzufügen
  * Deployment durchführen

8. Secrets konfigurieren
  * Unter "Applications" -> "Applications" -> "Basic Information":
    - Domain kopieren -> Als "DOMAIN" Secret hinzufügen
    - Client ID kopieren -> Als "CLIENT_ID" Secret hinzufügen
    - Client Secret kopieren -> Als "CLIENT_SECRET" Secret hinzufügen
  * Deployment nach jedem Secret

9. Default Role Code implementieren
  * Custom Code für automatische ROLE_TENANT Zuweisung
  * Role ID aus "User Management" -> "ROLE_TENANT" Settings kopieren
  * Code einfügen und anpassen (siehe Code-Block unten)
  * Finales Deployment durchführen

10. Action in Flow einbinden
   * Unter "Triggers" -> "Post Login"
   * "Add default role" zwischen Start und Complete ziehen
   * Flow speichern


🔐 **Ende AUTH0 SETUP**

🔐 **AUTH0 INTEGRATION**
17. Spring Boot Auth0 Konfiguration
    * application.yml anpassen:
      ```yaml
      okta:
        oauth2:
          issuer: https://dev-xxx.us.auth0.com/
          client-id: ${AUTH0_CLIENT_ID}
          client-secret: ${AUTH0_CLIENT_SECRET}
      ```
    * Environment Variables setzen:
      - .env Datei im Projekt-Root erstellen
      - Auth0 Credentials einfügen
      - .gitignore enthält bereits .env (Sicherheit!)


⚙️ **INFRASTRUCTURE LAYER**
18. Security Utilities implementieren (SecurityUtils.java)
   * Zentrale Konstanten:
     - Rollen-Definitionen (ROLE_TENANT, ROLE_LANDLORD)
     - Claims Namespace für Auth0 Integration

   * OAuth2 User Mapping:
     ```java
     public static User mapOauth2AttributesToUser(Map<String, Object> attributes)
     ```
     - Konvertiert OAuth2 Attribute in User-Entity
     - Verarbeitet verschiedene Attribute:
       • sub (unique identifier)
       • preferred_username
       • given_name/nickname
       • family_name
       • email
       • picture
       • roles/authorities
     - Fallback-Logik für fehlende Attribute

   * Authority/Roles Handling:
     - extractAuthorityFromClaims: Extrahiert Berechtigungen aus JWT Claims
     - getRolesFromClaims: Holt Rollen aus Claims-Namespace
     - mapRolesToGrantedAuthorities: Konvertiert Rollen zu Spring Security Authorities
     
   * Berechtigungsprüfung:
     ```java
     public static boolean hasCurrentUserAnyOfAuthorities(String ...authorities)
     ```
     - Prüft ob aktueller User bestimmte Berechtigungen hat
     - Unterstützt JWT und Standard Authentication
     - Stream-basierte Verarbeitung für Effizienz

   * Technische Details:
     - Statische Utility-Klasse
     - Verwendet Java Streams für Collections-Verarbeitung
     - Integration mit Spring Security Context
     - JWT Token Verarbeitung
     - Null-Safety und Validierung

   * Zweck:
     - Brücke zwischen Auth0 und Spring Security
     - Zentrale Verwaltung von Berechtigungslogik
     - Einheitliche User-Konvertierung
     - Vereinfachte Berechtigungsprüfungen


⚙️ **INFRASTRUCTURE LAYER**
19. Security Konfiguration implementieren (SecurityConfiguration.java)
    * Spring Security Setup:
      - @Configuration & @EnableMethodSecurity
      - Zentrale Sicherheitskonfiguration

    * SecurityFilterChain Bean:
      ```java
      @Bean
      public SecurityFilterChain configure(HttpSecurity http)
      ```
      - Öffentliche Endpoints:
        • GET: /api/tenant-listing/get-all-by-category
        • GET: /api/tenant-listing/get-one
        • POST: /api/tenant-listing/search
        • GET: /api/booking/check-availability
        • GET: /assets/*
      - Alle anderen Endpoints erfordern Authentifizierung
      - CSRF-Schutz via Cookies
      - OAuth2/OIDC Integration

    * Authorities Mapper Bean:
      ```java
      @Bean
      public GrantedAuthoritiesMapper userAuthoritiesMapper()
      ```
      - Konvertiert OAuth2 Claims zu Spring Security Authorities
      - Nutzt SecurityUtils für Extraktion
      - Ermöglicht rollenbasierte Autorisierung

    * Technische Features:
      - CSRF Protection
      - OAuth2 Login
      - JWT Token Support
      - Authorities Mapping

    * Zweck:
      - Zentrale Sicherheitskonfiguration
      - Definition von geschützten/öffentlichen Endpoints
      - Integration von Auth0 mit Spring Security


🔄 **SERVICE LAYER**
20. User DTO (Data Transfer Object) implementieren
    * Neuer application/dto Ordner für Service Layer
    * ReadUserDTO.java als Record:
      ```java
      public record ReadUserDTO(
          UUID publicId,        // Öffentliche ID
          String firstName,     // Vorname
          String lastName,      // Nachname
          String email,        // Email
          String imageUrl,     // Profilbild
          Set<String> authorities  // Berechtigungen
      )
      ```
    * Vorteile des Records:
      - Immutabilität (Unveränderbarkeit)
      - Automatische Getter
      - toString(), equals(), hashCode()
      - Kompakter Code
    
    * Zweck:
      - Sichere Datenübertragung
      - Trennung von Domain und API
      - Vermeidung von direkter Entity-Exposition


🔄 **SERVICE LAYER**
21. User Service und Mapper Implementierung

   A. UserService.java erstellen
      * @Service für Spring-Komponenten-Scanning
      * Dependencies:
        - UserRepository für Datenbankzugriffe
        - UserMapper für DTO-Konvertierung
      * @Transactional für Datenbankoperationen
      * Basis für User-bezogene Geschäftslogik

   B. UserMapper.java erweitern
      * Interface mit MapStruct Annotation
      * Mapping-Methoden:
        ```java
        ReadUserDTO readUserDTOToUser(User user);
        ```
      * Custom Mapping für Authorities:
        ```java
        default String mapAuthoritiesToString(Authority authority)
        ```
      * Zweck:
        - Automatische Konvertierung User → ReadUserDTO
        - Spezielle Behandlung von Authority-Objekten
        - Clean Code durch MapStruct-Generierung

   * Architektur-Kontext:
     - Service: Geschäftslogik & Transaktionsmanagement
     - Mapper: Daten-Transformation zwischen Schichten
     - Beide zentral für Service Layer Funktionalität


👤 **CONTROLLER LAYER**
22. Exception & REST Controller implementieren
    * UserException.java:
      - Custom Runtime Exception
      - Spezifisch für User-bezogene Fehler
      - Basis für klare Fehlermeldungen

    * AuthResource.java (REST Controller):
      * Endpunkte:
        ```java
        @GetMapping("/get-authenticated-user")  // User Daten abrufen
        @PostMapping("/logout")                 // Logout durchführen
        ```
      * Features:
        - OAuth2 Integration
        - Session Management
        - Fehlerbehandlung
      * Verbindung zu Service Layer über:
        - UserService
        - OAuth2 Client Registration

    * Zweck:
      - API-Endpunkte für Frontend
      - Request/Response Handling
      - Authentifizierung & Autorisierung


🎉 **Authentication & Authorization erfolgreich implementiert!**

📦 **MODEL LAYER (Listing Domain)**
23. Listing Value Objects (VOs) erstellen
   * DTO-Struktur aufbauen:
     - /listing/application/dto/vo/
     - Value Objects für Listing-Eigenschaften
   
   * Value Objects implementieren:
     ```java 
     public record BathsVO(@NotNull int value) {}
     ```
     - BathsVO, BedroomsVO, BedsVO etc.
     - NotNull-Validierung
     - Immutable Records für Datenkapselung
     
   * Zweck:
     - Validierung von Listing-Daten
     - Typensicherheit
     - Clean Architecture Pattern


📦 **MODEL LAYER (Listing Domain Fortsetzung)**
24. Listing Sub-DTOs implementieren 
   * Aufbauend auf Value Objects:
     - Kombiniert VOs zu größeren DTO-Einheiten
     - Hierarchische Strukturierung der Daten

   * Implementierte Sub-DTOs:
     - DescriptionDTO: Titel und Beschreibung (@NotNull)
     - LandlordListingDTO: Vermieter-Informationen
     - ListingInfoDTO: Unterkunfts-Details (Gäste, Zimmer, etc.)
     - PictureDTO: Bild-Verwaltung mit equals/hashCode

   * Unterschiede:
     - Simple DTOs (LandlordListingDTO) vs. Composite DTOs (ListingInfoDTO)
     - Primitive Typen vs. Value Objects
     - Standard vs. Custom equals/hashCode (PictureDTO)

   * Beziehung zu VOs:
     - VOs: Einzelne Werte mit Validierung
     - Sub-DTOs: Gruppierung zusammengehöriger VOs/Daten
     - Hierarchische Datenstruktur

   * Zweck:
     - Strukturierte Datenhaltung
     - Validierung auf verschiedenen Ebenen
     - Klare Trennung von Datenstrukturen
     - Vorbereitung für Frontend-Kommunikation


📦 **MODEL LAYER (Listing Domain Fortsetzung)**
25. SaveListingDTO implementieren
   * Hauptkomponente für Listing-Erstellung:
     - Kombiniert alle Sub-DTOs und VOs
     - Verwendet Java Class statt Record
     - Vollständige Getter/Setter
     
   * Strukturelle Elemente:
     ```java 
     @NotNull BookingCategory category
     @NotNull String location
     @NotNull @Valid ListingInfoDTO infos
     @NotNull @Valid DescriptionDTO description
     @NotNull @Valid PriceVO price
     @NotNull List<PictureDTO> pictures
     ```

   * Besonderheiten:
     - Validierung auf Top-Level (@NotNull) und Nested (@Valid)
     - Mutable Objekt (anders als Sub-DTOs)
     - Aggregiert alle Listing-bezogenen Daten

   * Beziehung zu vorherigen DTOs:
     - Verwendet Sub-DTOs aus step 24
     - Bildet oberste Ebene der DTO-Hierarchie
     
   * Zweck:
     - Datencontainer für Listing-Erstellung
     - Frontend-Backend Kommunikation
     - Validierung der Gesamtstruktur


🔄 **SERVICE LAYER (Multiple Domains)**
26. Mapper und DTOs erweitern
    * ListingMapper.java:
      - Komplexe Mappings zwischen DTOs und Entities
      - Ignoriert sensitive Felder (publicId, etc.)
      - Nested Mappings für verschachtelte Objekte
      - Custom Default-Methoden

    * Neue DTOs:
      - CreatedListingDTO: Minimales Response-DTO
      - SaveListingDTO: Request-DTO für Listing-Erstellung


27. Auth0 Integration erweitern
    * Auth0Service.java implementiert:
      - Verwaltet Auth0 API Zugriffe
      - Rollenmanagement für Landlords
      - Token-Handling
      - Exception-Handling
    
    * Features:
      - Automatische Rollenzuweisung
      - API Token Management
      - User Lookup via Email

    * Zweck:
      - Verbindung zwischen Domain und Auth0
      - Rollenbasierte Zugriffskontrolle
      - Sicheres Token-Management


🔄 **SERVICE LAYER (Listing Domain)**
28. Service Layer für Listings implementieren
   * LandlordService.java:
     - Zentrale Business-Logik für Vermieter
     - Funktionen:
       • Listing erstellen
       • Properties verwalten
       • Berechtigungsprüfungen
     - Transaktionsverwaltung (@Transactional)
     - Integration mit Auth0Service

   * PictureService.java:
     - Spezialisiert auf Bildverwaltung
     - Features:
       • Batch-Speicherung von Bildern
       • Cover-Bild Markierung
       • Listing-Bild Verknüpfung
     - Eigenständiger Service für bessere Trennung

29. Mapper für Bilder implementieren
   * ListingPictureMapper.java:
     - Mapping zwischen DTOs und Entities
     - Spezielle Funktionen:
       • Extract Cover Picture
       • Batch Conversions
     - Named Mappings für spezielle Fälle

   * Architektur-Highlights:
     - Klare Trennung der Verantwortlichkeiten
     - Service-Orchestrierung
     - Transaktionale Sicherheit
     - Domain-driven Design Prinzipien

   * Zweck:
     - Vollständige Business-Logik für Listings
     - Sicheres Bildermanagement
     - Integration verschiedener Services
     - Saubere Datentransformation


**Exkurs: Zusammenfassung bis jetzt**

📦 Model Layer
- User Domain (Authority, User)
- Listing Domain (Listing, ListingPicture, BookingCategory)
- Shared Kernel (AbstractAuditingEntity)

🗄️ Repository Layer
- UserRepository
- ListingRepository
- ListingPictureRepository

🔄 Service Layer
- Auth0Service (Authentifizierung)
- LandlordService (Listing-Verwaltung)
- PictureService (Bildverwaltung)

🔐 Auth0 Integration
- Custom Actions
- Role Management
- Token Handling


👤 **CONTROLLER LAYER (Listing Domain)**
30. LandlordResource Implementation
   * REST Controller für Listing-Management:
     ```java
     @RestController
     @RequestMapping("/api/landlord-listing")
     ```

   * Features:
     - Multipart Datei-Upload für Bilder
     - DTO Validierung
     - Fehlerbehandlung
     - JSON Konvertierung

   * Hauptendpoint create():
     ```java
     @PostMapping("/create")
     ```
     - Verarbeitet Listing-Daten und Bilder
     - Validiert SaveListingDTO
     - Wandelt MultipartFiles in PictureDTOs um

   * Technische Highlights:
     - MediaType.MULTIPART_FORM_DATA
     - Custom Validation
     - ResponseEntity Builder Pattern
     - Stream API für Bildverarbeitung

   * Zweck:
     - API-Endpunkt für Vermieter
     - Sicheres File-Handling
     - Input-Validierung
     - Fehler-Reporting


🗄️ **DATABASE LAYER** (Die statischen Assets sind letztendlich auch eine Form von Datenspeicherung, auch wenn sie nicht in der PostgreSQL Datenbank liegen).
31. Static Resources Configuration
  * Struktur:
    ```
    src/main/resources/
    └── static/
        └── assets/
            └── countries.json
    ```

  * Countries Data:
    - Comprehensive JSON (39,195 lines)
    - Country Details:
      • Names
      • Codes
      • Currencies
      • Languages
      • Geographic Data
      • Flags

  * Features:
    - Static Resource Serving
    - Frontend Integration
    - Performance Optimierung
    - Offline Support

  * Vorteile:
    - Schnelle Datenverfügbarkeit
    - Keine API-Abhängigkeit
    - Reduzierte Latenz
    - Konsistente Daten

  * Zweck:
    - Location Step Support
    - Country Selection
    - Map Integration
    - Geocoding Basis


📦 **MODEL LAYER** (Listing Domain)
32. DisplayCardListingDTO Implementation
   * Record-basiertes DTO:
     ```java
     public record DisplayCardListingDTO(
       PriceVO price,
       String location,
       PictureDTO cover,
       BookingCategory bookingCategory,
       UUID publicId
     )
     ```

   * Features:
     - Minimale Datenstruktur für Listing-Cards
     - Value Objects für Typsicherheit
     - Immutable durch Record
     - UUID für sichere Referenzierung

   * Zweck:
     - Frontend Card Display
     - Performance-optimierte Datenübertragung
     - Reduzierte Datenmenge
     - Sichere Identifizierung


⚙️ **INFRASTRUCTURE LAYER**
33. State Management System Implementation
   * Core Komponenten:
     1. State.java - Generic State Container
        ```java
        public class State<T, V> {
          private T value;    // Success value
          private V error;    // Error value
          private StatusNotification status;
        }
        ```

     2. StateBuilder.java - Fluent Builder
        ```java
        public class StateBuilder<T, V> {
          forSuccess()
          forError()
          forUnauthorized()
        }
        ```

     3. StatusNotification.java - Status Enum
        ```java
        public enum StatusNotification {
          OK, ERROR, UNAUTHORIZED
        }
        ```

   * Features:
     - Generische Typisierung
     - Builder Pattern
     - Immutable States
     - Error Handling
     - Status Management

   * Zweck:
     - Einheitliches Error Handling
     - Typ-sichere Responses
     - Klare Status-Kommunikation
     - Wiederverwendbare Struktur

**Zusätzliche Erklärungen:**
- Das State System ermöglicht konsistentes Error Handling
- Der Builder vereinfacht die State-Erstellung
- Die Generics erlauben flexible Typen für Erfolgs- und Fehlerfälle
- Das System ist domänenübergreifend im SharedKernel


🗄️ **REPOSITORY LAYER** (Listing Domain)
34. ListingRepository Erweiterung
  * Custom Query Implementation:
    ```java
    @Query("SELECT listing FROM Listing listing 
           LEFT JOIN FETCH listing.pictures picture 
           WHERE listing.landlordPublicId = :landlordPublicId 
           AND picture.isCover = true")
    List<Listing> findAllByLandlordPublicIdFetchCoverPicture(UUID landlordPublicId);
    ```
  * Features:
    - Optimierte Join Fetch Query
    - Cover Picture Filterung
    - Landlord-spezifische Listings
    - N+1 Problem Vermeidung

🔄 **SERVICE LAYER**
35. Mapper Erweiterung für Display Cards
  * ListingMapper Updates:
    ```java
    @Mapping(target = "cover", source = "pictures", 
            qualifiedByName = "extract-cover")
    DisplayCardListingDTO listingToDisplayCardListingDTO(Listing listing);
    
    List<DisplayCardListingDTO> listingToDisplayCardListingDTOs(List<Listing> listings);
    ```

  * ListingPictureMapper Integration:
    ```java
    @Named("extract-cover")
    default PictureDTO extractCover(Set<ListingPicture> pictures)
    ```

  * Features:
    - Batch Mapping Support
    - Cover Bild Extraktion
    - Qualifizierte Mappings
    - Stream API Nutzung

  * Zweck:
    - Effiziente Kartendarstellung
    - Performance-optimierte Queries
    - Sauberes Domain Mapping
    - Reduzierte Datenlast

**Zusätzliche Erklärungen:**
- Die Repository-Query nutzt JOIN FETCH für Performance
- MapStruct handhabt komplexe Object-Mappings
- Der Cover-Extraktor nutzt Java Streams
- Die Implementierung bereitet List-View vor


🔄 **SERVICE LAYER**
36. LandlordService Erweiterung
  * Neue Service Methoden:
    ```java
    @Transactional(readOnly = true)
    public List<DisplayCardListingDTO> getAllProperties(ReadUserDTO landlord)

    @Transactional
    public State<UUID, String> delete(UUID publicId, ReadUserDTO landlord)
    ```

  * Features:
    - Readonly Transaction für Queries
    - State Pattern für Delete Response
    - Authorisierungsprüfung beim Löschen
    - Optimierte Property Abfrage

👤 **CONTROLLER LAYER**
37. LandlordResource Erweiterung
  * Neue Endpoints:
    ```java
    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    ResponseEntity<List<DisplayCardListingDTO>> getAll()

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    ResponseEntity<UUID> delete(@RequestParam UUID publicId)
    ```

  * Security Features:
    - Role-basierte Zugriffskontrolle
    - Pre-Authorization Checks
    - Status-basierte Responses
    - Sichere UUID Handling

  * Response Handling:
    - OK (200): Erfolgreicher Delete/Get
    - UNAUTHORIZED (401): Keine Berechtigung
    - INTERNAL_SERVER_ERROR (500): Systemfehler

  * Zweck:
    - Property Management für Vermieter
    - Sichere Löschoperationen
    - Effiziente Listendarstellung
    - Authorisierte Zugriffe

**Zusätzliche Erklärungen:**
- @Transactional(readOnly = true) optimiert DB-Zugriffe
- State Pattern ermöglicht typsichere Responses
- PreAuthorize sichert Endpoints auf Methodenebene
- ResponseEntity bietet feingranulare HTTP-Kontrolle


🗄️ **REPOSITORY LAYER** (Listing Domain)
38. ListingRepository Erweiterung für Home Page
  * Neue Query Methoden:
    ```java
    @Query("SELECT listing from Listing listing 
           LEFT JOIN FETCH listing.pictures picture 
           WHERE picture.isCover = true 
           AND listing.bookingCategory = :bookingCategory")
    Page<Listing> findAllByBookingCategoryWithCoverOnly(
      Pageable pageable, 
      BookingCategory bookingCategory
    );

    @Query("SELECT listing from Listing listing 
           LEFT JOIN FETCH listing.pictures picture 
           WHERE picture.isCover = true")
    Page<Listing> findAllWithCoverOnly(Pageable pageable);
    ```

  * Features:
    - Paginierung durch Pageable
    - Performance-optimierte Joins
    - Cover-Bild Filter
    - Kategorie-basierte Filterung
    - Optional Category Filter

  * Optimierungen:
    - LEFT JOIN FETCH für eager loading
    - Cover Picture Filterung in Query
    - Vermeidung N+1 Problem
    - Effiziente Datenabruf

  * Zweck:
    - Homepage Listing Display
    - Kategoriefilterung
    - Paginierte Ergebnisse
    - Performance-optimierte Abfragen

**Zusätzliche Technische Details:**
- Page Interface für Pagination Support
- JPQL für optimierte Queries
- Fetch Join für Performance
- Conditional Category Filter


🔄 **SERVICE LAYER** (Tenant Domain)
39. Tenant Service Implementation
  * Service Definition:
    ```java
    @Service
    public class TenantService {
      private final ListingRepository listingRepository;
      private final ListingMapper listingMapper;
      private final UserService userService;
    }
    ```

  * Core Features:
    ```java
    public Page<DisplayCardListingDTO> getAllByCategory(
      Pageable pageable, 
      BookingCategory category
    ) {
      // Kategoriebasierte Listing-Suche mit Pagination
    }
    ```

  * Business Logic:
    - ALL Category Handling
    - Kategorie-spezifische Suche
    - Pagination Support
    - DTO Mapping

👤 **CONTROLLER LAYER** (Tenant Domain)
40. Tenant Resource Implementation
  * Controller Definition:
    ```java
    @RestController
    @RequestMapping("/api/tenant-listing")
    public class TenantResource {
      private final TenantService tenantService;
    }
    ```

  * Endpoint:
    ```java
    @GetMapping("/get-all-by-category")
    public ResponseEntity<Page<DisplayCardListingDTO>> 
      findAllByBookingCategory(
        Pageable pageable,
        @RequestParam BookingCategory category
      )
    ```

  * Features:
    - Öffentlicher Endpunkt
    - Pagination Support
    - Category Filter
    - Response Entity Wrapping

  * Zweck:
    - Homepage Listing Display
    - Kategorie-basierte Filterung
    - Paginierte Ergebnisse
    - RESTful API Design


🗄️ **REPOSITORY LAYER**
41. ListingRepository Ergänzung
  * Neue Query Methode:
    ```java
    Optional<Listing> findByPublicId(UUID publicId);
    ```
  * Zweck: Einzelnes Listing über Public ID finden

📦 **MODEL LAYER**
42. DisplayListingDTO Implementation
  * Komplexes DTO für detaillierte Listing-Ansicht:
    ```java
    public class DisplayListingDTO {
      private DescriptionDTO description;
      private List<PictureDTO> pictures;
      private ListingInfoDTO infos;
      private PriceVO price;
      private LandlordListingDTO landlord;
      // ...getter/setter
    }
    ```
  * Features:
    - Vollständige Listing Details
    - Nested DTOs
    - Value Objects
    - Landlord Informationen

## 🔄 SERVICE LAYER
43. Mapping & Service Erweiterung

  * ListingMapper Erweiterung:
    ```java
    @Mapping(target = "description.title.value", source = "title")
    @Mapping(target = "description.description.value", source = "description")
    // ...weitere Mappings
    DisplayListingDTO listingToDisplayListingDTO(Listing listing);
    ```

  * TenantService Erweiterung:
    ```java
    public State<DisplayListingDTO, String> getOne(UUID publicId)
    ```
    Features:
    - Optional Handling
    - Error State Management
    - Landlord Integration
    - Transaktionale Sicherheit

👤 **CONTROLLER LAYER**
44. TenantResource Erweiterung
  * Neuer Endpoint:
    ```java
    @GetMapping("/get-one")
    public ResponseEntity<DisplayListingDTO> getOne(
      @RequestParam UUID publicId
    )
    ```
  * Features:
    - State-basierte Response
    - Problem Detail für Errors
    - Parametrisierte Anfragen
    - REST-konformes Design

**Zweck:**
- Detailansicht einzelner Listings
- Vollständige Listing-Informationen
- Error Handling
- Landlord Integration

📦 **MODEL LAYER (Booking Domain)** 
45. Booking DTOs Implementation
  * NewBookingDTO:
    ```java
    public record NewBookingDTO(
      @NotNull OffsetDateTime startDate,
      @NotNull OffsetDateTime endDate,
      @NotNull UUID listingPublicId
    ) {}
    ```

  * BookedDateDTO:
    ```java
    public record BookedDateDTO(
      @NotNull OffsetDateTime startDate,
      @NotNull OffsetDateTime endDate
    ) {}
    ```

  * Features:
    - Record-basierte DTOs
    - Validierung mit @NotNull
    - OffsetDateTime für Zeitzonen-Support
    - Immutable Design


🔄 **SERVICE LAYER (Booking Domain)**
46. BookingMapper Implementation
  * Interface Definition:
    ```java
    @Mapper(componentModel = "spring")
    public interface BookingMapper {
      // Konvertiert DTO zu Domain Entity
      Booking newBookingToBooking(NewBookingDTO newBookingDTO);
      // Extrahiert Buchungsdaten für Verfügbarkeitsprüfung
      BookedDateDTO bookingToCheckAvailability(Booking booking);
    }
    ```

  * Features:
    - Automatische MapStruct Implementation
    - Bidirektionales Mapping
    - Clean Separation of Concerns
    - Type-safe Konvertierung

  * Zentrale Aufgaben:
    - Saubere Trennung zwischen API und Domain Layer
    - Typ-sichere Konvertierung von DTOs
    - Vereinfachung der Verfügbarkeitsprüfung
    - Reduzierung von Boilerplate Code


🗄️ **REPOSITORY LAYER (Booking Domain)**
47. BookingRepository Implementation
  * Core Queries:
    ```java
    @Query("SELECT case when count(booking) > 0 
           then true else false end from Booking booking 
           WHERE NOT (booking.endDate <= :startDate 
           or booking.startDate >= :endDate)
           AND booking.fkListing = :fkListing")
    boolean bookingExistsAtInterval(
      OffsetDateTime startDate, 
      OffsetDateTime endDate, 
      UUID fkListing
    );

    List<Booking> findAllByFkListing(UUID fkListing);
    ```

  * Features:
    - Effiziente Überlappungs-Prüfung
    - Listing-spezifische Abfragen
    - Optimierte JPQL Queries
    - Boolean Rückgabewert

  * Kernfunktionen:
    - Effiziente Buchungsverwaltung
    - Vermeidung von Überbuchungen
    - Schnelle Verfügbarkeitsabfragen
    - Performance-optimierte Datenbankzugriffe


📦 **MODEL LAYER (Booking Domain)**
48. ListingCreateBookingDTO Implementation
  * DTO Definition:
    ```java
    public record ListingCreateBookingDTO(
      UUID listingPublicId, 
      PriceVO price
    ) {}
    ```
  * Features:
    - Minimales DTO für Buchungserstellung
    - Value Object Integration (PriceVO)
    - Immutable Design


🔄 **SERVICE LAYER (Booking Domain)**
49. Booking Service Implementation
  * Dependencies Setup:
    ```java
    public class BookingService {
      private final BookingRepository bookingRepository;
      private final BookingMapper bookingMapper;
      private final UserService userService;
      private final LandlordService landlordService;
      
      // Constructor Injection
      public BookingService(BookingRepository bookingRepository, ...)
    ```

  * Create Booking Methode:
    ```java
    @Transactional
    public State<Void, String> create(NewBookingDTO newBookingDTO) {
      // Booking Entity Erstellung
      // Verfügbarkeitsprüfung
      // Preisberechnung
      // Persistierung
    }
    ```

  * Features:
    - Transaktionale Sicherheit
    - Validierung der Verfügbarkeit
    - Preiskalkulation
    - State-basiertes Error Handling
    - User Context Integration


🔄 **SERVICE LAYER (Landlord Domain)**
50. LandlordService Erweiterung
  * Mapper Ergänzung:
    ```java
    @Mapping(target = "listingPublicId", source = "publicId")
    ListingCreateBookingDTO mapListingToListingCreateBookingDTO(
      Listing listing
    );
    ```

  * Neue Service Methode:
    ```java
    public Optional<ListingCreateBookingDTO> getByListingPublicId(
      UUID publicId
    ) {
      return listingRepository.findByPublicId(publicId)
        .map(listingMapper::mapListingToListingCreateBookingDTO);
    }
    ```

### Core Features:
  * Booking Creation Flow:
    - Listing Verfügbarkeit prüfen
    - Daten validieren
    - Preis berechnen
    - Booking persistieren
    - Error States handhaben

  * Validierungen:
    - Listing Existenz
    - Verfügbarkeit im Zeitraum
    - User Authentication
    - Preisberechnung

  * Transaktionsmanagement:
    - ACID Eigenschaften
    - Rollback bei Fehlern
    - Konsistente Datenhaltung

📦 **MODEL LAYER (Booking Domain)**
51. BookedListingDTO Implementation
 * Record Definition:
   ```java
   public record BookedListingDTO(
     @Valid PictureDTO cover,
     @NotEmpty String location, 
     @Valid BookedDateDTO dates,
     @Valid PriceVO totalPrice,
     @NotNull UUID bookingPublicId,
     @NotNull UUID listingPublicId
   ) {}
   ```
 * Features:
   - Record für Immutability
   - Validierungsannotationen
   - Value Objects für Typsicherheit
   - Eindeutige IDs für Booking und Listing

🗄️ **REPOSITORY LAYER**
52. Repository Erweiterungen
 * BookingRepository:
   ```java
   List<Booking> findAllByFkTenant(UUID fkTenant);
   ```
 * ListingRepository:
   ```java
   List<Listing> findAllByPublicIdIn(List<UUID> allListingPublicIDs);
   ```

🔄 **SERVICE LAYER**
53. Service Layer Erweiterungen
 * LandlordService:
   ```java
   public List<DisplayCardListingDTO> getCardDisplayByListingPublicId(
     List<UUID> allListingPublicIDs
   ) {
     return listingRepository.findAllByPublicIdIn(allListingPublicIDs)
       .stream()
       .map(listingMapper::listingToDisplayCardListingDTO)
       .toList();
   }
   ```

 * BookingService:
   ```java
   @Transactional(readOnly = true)
   public List<BookedListingDTO> getBookedListing() {
     ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
     List<Booking> allBookings = bookingRepository.findAllByFkTenant(connectedUser.publicId());
     List<UUID> allListingPublicIDs = allBookings.stream()
       .map(Booking::getFkListing)
       .toList();
     List<DisplayCardListingDTO> allListings = landlordService
       .getCardDisplayByListingPublicId(allListingPublicIDs);
     return mapBookingToBookedListing(allBookings, allListings);
   }
   ```
 * Mapping Logik:
   - Stream-basierte Verarbeitung
   - Filter nach Listing ID
   - Transformation zu BookedListingDTO
   - Preisberechnung und Datumskonvertierung

 * Zweck:
   - Abruf aller Buchungen eines Tenants
   - Zusammenführung von Booking und Listing Daten
   - Effiziente Datentransformation
   - Read-only Transaktionssicherheit


🔄 **SERVICE LAYER**
54. BookingService Cancel Implementation
 * Cancel Methode:
   ```java
   @Transactional
   public State<UUID, String> cancel(
     UUID bookingPublicId, 
     UUID listingPublicId, 
     boolean byLandlord
   ) {
     ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
     int deleteSuccess = 0;

     if (SecurityUtils.hasCurrentUserAnyOfAuthorities(SecurityUtils.ROLE_LANDLORD) 
         && byLandlord) {
       deleteSuccess = handleDeletionForLandlord(
         bookingPublicId, 
         listingPublicId, 
         connectedUser, 
         deleteSuccess
       );
     } else {
       deleteSuccess = bookingRepository
         .deleteBookingByFkTenantAndPublicId(
           connectedUser.publicId(), 
           bookingPublicId
         );
     }

     return deleteSuccess >= 1 
       ? State.<UUID, String>builder().forSuccess(bookingPublicId)
       : State.<UUID, String>builder().forError("Booking not found");
   }
   ```
 * Features:
   - Transaktionale Sicherheit
   - Rollenbasierte Stornierung
   - State Pattern für Responses
   - Validierung des Löschvorgangs

👤 **CONTROLLER LAYER**
55. BookingResource Endpunkte
 * Neue Endpunkte:
   ```java
   @GetMapping("get-booked-listing")
   public ResponseEntity<List<BookedListingDTO>> getBookedListing()

   @DeleteMapping("cancel")
   public ResponseEntity<UUID> cancel(
     @RequestParam UUID bookingPublicId,
     @RequestParam UUID listingPublicId,
     @RequestParam boolean byLandlord
   )
   ```
 * Features:
   - GET für Buchungsübersicht 
   - DELETE für Stornierungen
   - Query Parameter Handling
   - ProblemDetail für Errors
   - Typed Responses

 * Zweck:
   - RESTful API Design
   - Fehlerbehandlung mit HTTP Status
   - Typensichere Endpunkte
   - Flexible Stornierungsoptionen


# BACKEND_TUTORIAL_NOTES.md (Fortsetzung)

🗄️ **REPOSITORY LAYER**
56. Repository Erweiterungen für Cancel-Flow
 * BookingRepository:
   ```java
   int deleteBookingByFkTenantAndPublicId(
     UUID tenantPublicId, 
     UUID bookingPublicId
   );
   
   int deleteBookingByPublicIdAndFkListing(
     UUID bookingPublicId, 
     UUID listingPublicId
   );
   ```

 * ListingRepository:
   ```java
   Optional<Listing> findOneByPublicIdAndLandlordPublicId(
     UUID listingPublicId, 
     UUID landlordPublicId
   );
   ```

🔄 **SERVICE LAYER**
57. Erweiterte Cancel Logik
 * LandlordService:
   ```java
   @Transactional(readOnly = true)
   public Optional<DisplayCardListingDTO> getByPublicIdAndLandlordPublicId(
     UUID listingPublicId, 
     UUID landlordPublicId
   ) {
     return listingRepository
       .findOneByPublicIdAndLandlordPublicId(listingPublicId, landlordPublicId)
       .map(listingMapper::listingToDisplayCardListingDTO);
   }
   ```

 * BookingService:
   - Landlord Deletion Handler:
     ```java
     private int handleDeletionForLandlord(
       UUID bookingPublicId,
       UUID listingPublicId,
       ReadUserDTO connectedUser,
       int deleteSuccess
     ) {
       Optional<DisplayCardListingDTO> listingVerificationOpt = 
         landlordService.getByPublicIdAndLandlordPublicId(
           listingPublicId, 
           connectedUser.publicId()
         );
       
       if (listingVerificationOpt.isPresent()) {
         deleteSuccess = bookingRepository.deleteBookingByPublicIdAndFkListing(
           bookingPublicId,
           listingVerificationOpt.get().publicId()
         );
       }
       return deleteSuccess;
     }
     ```

 * Features:
   - Rollenbasierte Stornierung
   - Verifizierung der Landlord-Berechtigung
   - Transaktionale Sicherheit
   - State-basiertes Response Handling

 * Zweck:
   - Sichere Stornierungslogik
   - Berechtigungsprüfung
   - Datenintegrität
   - Fehlerbehandlung


🔄 **SERVICE LAYER**
58. BookingService Landlord View Implementation
 * Neue Service Methode:
   ```java
   @Transactional(readOnly = true)
   public List<BookedListingDTO> getBookedListingForLandlord() {
     ReadUserDTO connectedUser = userService
       .getAuthenticatedUserFromSecurityContext();
     
     List<DisplayCardListingDTO> allProperties = 
       landlordService.getAllProperties(connectedUser);
     
     List<UUID> allPropertyPublicIds = allProperties.stream()
       .map(DisplayCardListingDTO::publicId)
       .toList();
     
     List<Booking> allBookings = bookingRepository
       .findAllByFkListingIn(allPropertyPublicIds);
     
     return mapBookingToBookedListing(allBookings, allProperties);
   }
   ```

 * Features:
   - Landlord-spezifische Buchungsübersicht
   - Stream-basierte ID Extraktion
   - Effiziente Datenaggregation
   - Read-only Transaktion

🗄️ **REPOSITORY LAYER**
59. BookingRepository Erweiterung
 ```java
 List<Booking> findAllByFkListingIn(List<UUID> allPropertyPublicIds);
 ```

👤 **CONTROLLER LAYER**
60. BookingRepository Erweiterung
 ```java
@GetMapping("get-booked-listing-for-landlord")
@PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
public ResponseEntity<List<BookedListingDTO>> getBookedListingForLandlord() {
  return ResponseEntity.ok(bookingService.getBookedListingForLandlord());
}
 ```

 * Features:
   - Rollenbasierte Zugriffskontrolle
   - REST Endpoint für Landlord View
   - Typed Response
   - Authorization Check


📦 **MODEL LAYER**
61. SearchDTO Implementation
 ```java
 public record SearchDTO(
   @Valid BookedDateDTO dates,
   @Valid ListingInfoDTO infos,
   @NotEmpty String location
 ) {}
 ```

 * Features:
   - Record für Immutability
   - Validierung durch Annotations
   - Kombination vorhandener DTOs
   - Location-basierte Suche


🗄️ **REPOSITORY LAYER**
62. BookingRepository Suchabfrage
 ```java
@Query(
  "SELECT booking FROM Booking booking WHERE " +
  "NOT (booking.endDate <= :startDate or booking.startDate >= :endDate) " +
  "AND booking.fkListing IN :fkListings"
)
List<Booking> findAllMatchWithDate(
  List<UUID> fkListings, 
  OffsetDateTime startDate, 
  OffsetDateTime endDate
);
 ```

 * Features:
   - Custom JPQL Query
   - Datum-Überschneidungsprüfung
   - Listing-basierte Filterung
   - Effiziente Datenbankabfrage


🔄 **SERVICE LAYER**
63. BookingService Sucherweiterung

 ```java
public List<UUID> getBookingMatchByListingIdsAndBookedDate(
  List<UUID> listingsId, 
  BookedDateDTO bookedDateDTO
) {
  return bookingRepository
    .findAllMatchWithDate(
      listingsId, 
      bookedDateDTO.startDate(), 
      bookedDateDTO.endDate()
    )
    .stream()
    .map(Booking::getFkListing)
    .toList();
}
 ```

 * Features:
   - Verfügbarkeitsprüfung
   - Stream-basierte ID Extraktion
   - DTO zu Entity Mapping
   - Effiziente Datenbankabfrage


# 🔍 BACKEND IMPLEMENTATION (Tenant Domain)
## 64. Such-Funktionalität Implementation

### A. Repository Layer Erweiterung
* ListingRepository.java:
  * `Page<Listing> findAllByLocationAndBathroomsAndBedroomsAndGuestsAndBeds(Pageable pageable, String location, int bathrooms, int bedrooms, int guests, int beds);`
  * Paginierte Suche nach Listing-Kriterien
  * Automatische Query-Generierung durch Spring Data

### B. Service Layer
* TenantService.java Erweiterung:
  * BookingService Integration:
    * `private final BookingService bookingService;`
  * Such-Methode:
    * `@Transactional(readOnly = true)`
    * `public Page<DisplayCardListingDTO> search(Pageable pageable, SearchDTO newSearch)`
  * Features:
    * Matching nach Basis-Kriterien
    * Filterung nach Verfügbarkeit
    * Stream-basierte Verarbeitung
    * Pagination Support

### C. Controller Layer
* TenantResource.java:
  * `@PostMapping("/search")`
  * `public ResponseEntity<Page<DisplayCardListingDTO>> search(Pageable pageable, @Valid @RequestBody SearchDTO searchDTO)`

### D. Security Configuration
* SecurityConfiguration.java:
  * `.requestMatchers(HttpMethod.POST, "api/tenant-listing/search").permitAll()`
  * Öffentlicher Zugriff auf Such-Endpoint
  * POST-Methode erlaubt
  * CSRF-Schutz aktiv