package com.architectcoders.wanago.data.server

data class RemoteSearchResult(
    val _embedded: Embedded,
    val _links: Map<String, Link>,
    val page: Page
)

data class Embedded(
    val events: List<RemoteEvent>
)

data class Page(
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)

data class RemoteEvent(
    val _embedded: EmbeddedX,
    val _links: EventLinks,
    val accessibility: Accessibility,
    val ageRestrictions: AgeRestrictions,
    val classifications: List<Classification>,
    val dates: Dates,
    val id: String,
    val images: List<Image>,
    val locale: String,
    val name: String,
    val pleaseNote: String,
    val priceRanges: List<PriceRange>,
    val products: List<Product>,
    val promoter: Promoter,
    val promoters: List<Promoter>,
    val sales: Sales,
    val seatmap: Seatmap,
    val test: Boolean,
    val ticketLimit: TicketLimit,
    val ticketing: Ticketing,
    val type: String,
    val url: String
)

data class EmbeddedX(
    val attractions: List<Attraction>,
    val venues: List<Venue>
)

data class EventLinks(
    val attractions: List<Link>,
    val self: Link,
    val venues: List<Link>
)

data class Accessibility(
    val info: String,
    val ticketLimit: Int
)

data class AgeRestrictions(
    val legalAgeEnforced: Boolean
)

data class Dates(
    val spanMultipleDays: Boolean,
    val start: Start,
    val status: Status,
    val timezone: String
)

data class Image(
    val fallback: Boolean,
    val height: Int,
    val ratio: String,
    val url: String,
    val width: Int
)

data class PriceRange(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)

data class Product(
    val classifications: List<Classification>,
    val id: String,
    val name: String,
    val type: String,
    val url: String
)

data class Promoter(
    val description: String,
    val id: String,
    val name: String
)

data class Sales(
    val presales: List<Presale>,
    val `public`: Public
)

data class Seatmap(
    val staticUrl: String
)

data class TicketLimit(
    val info: String
)

data class Ticketing(
    val safeTix: SafeTix
)

data class Attraction(
    val _links: Map<String, Link>,
    val aliases: List<String>,
    val classifications: List<Classification>,
    val externalLinks: ExternalLinks,
    val id: String,
    val images: List<Image>,
    val locale: String,
    val name: String,
    val test: Boolean,
    val type: String,
    val upcomingEvents: UpcomingEvents,
    val url: String
)

data class Venue(
    val _links: Map<String, Link>,
    val accessibleSeatingDetail: String,
    val address: Address,
    val boxOfficeInfo: BoxOfficeInfo,
    val city: City,
    val country: Country,
    val dmas: List<Dma>,
    val generalInfo: GeneralInfo,
    val id: String,
    val locale: String,
    val location: Location,
    val markets: List<Market>,
    val name: String,
    val parkingDetail: String,
    val postalCode: String,
    val state: State,
    val test: Boolean,
    val timezone: String,
    val type: String,
    val upcomingEvents: UpcomingEventsX,
    val url: String
)

data class Link(
    val href: String
)

data class Classification(
    val family: Boolean,
    val genre: Genre,
    val primary: Boolean,
    val segment: Segment,
    val subGenre: SubGenre,
    val subType: SubType,
    val type: Type
)

data class ExternalLinks(
    val facebook: List<Facebook>,
    val homepage: List<Homepage>,
    val instagram: List<Instagram>,
    val twitter: List<Twitter>,
    val wiki: List<Wiki>
)

data class UpcomingEvents(
    val _filtered: Int,
    val _total: Int,
    val ticketmaster: Int,
    val tmr: Int
)

data class Genre(
    val id: String,
    val name: String
)

data class Segment(
    val id: String,
    val name: String
)

data class SubGenre(
    val id: String,
    val name: String
)

data class SubType(
    val id: String,
    val name: String
)

data class Type(
    val id: String,
    val name: String
)

data class Facebook(
    val url: String
)

data class Homepage(
    val url: String
)

data class Instagram(
    val url: String
)

data class Twitter(
    val url: String
)

data class Wiki(
    val url: String
)

data class Address(
    val line1: String
)

data class BoxOfficeInfo(
    val openHoursDetail: String,
    val phoneNumberDetail: String,
    val willCallDetail: String
)

data class City(
    val name: String
)

data class Country(
    val countryCode: String,
    val name: String
)

data class Dma(
    val id: Int
)

data class GeneralInfo(
    val childRule: String
)

data class Location(
    val latitude: String,
    val longitude: String
)

data class Market(
    val id: String,
    val name: String
)

data class State(
    val name: String,
    val stateCode: String
)

data class UpcomingEventsX(
    val _filtered: Int,
    val _total: Int,
    val ticketmaster: Int
)

data class Start(
    val dateTBA: Boolean,
    val dateTBD: Boolean,
    val dateTime: String,
    val localDate: String,
    val localTime: String,
    val noSpecificTime: Boolean,
    val timeTBA: Boolean
)

data class Status(
    val code: String
)

data class Presale(
    val description: String,
    val endDateTime: String,
    val name: String,
    val startDateTime: String
)

data class Public(
    val endDateTime: String,
    val startDateTime: String,
    val startTBA: Boolean,
    val startTBD: Boolean
)

data class SafeTix(
    val enabled: Boolean
)

data class First(
    val href: String
)

data class Last(
    val href: String
)

data class Next(
    val href: String
)