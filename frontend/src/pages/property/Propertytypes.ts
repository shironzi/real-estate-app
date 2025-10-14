export type PropertyTypesView = {
    id: string;
    title: string;
    price: number;
    address: string;
    description: string;
    status: string;
    maxGuest: number;
    totalBedroom: number;
    totalBed: number;
    totalBath: number;
    city: string;
    propertyType: "APARTMENT" | "HOUSE" | "VILLA" | "CABIN";
    image: string[]
};

export const PropertyTypesViewDefaultData: PropertyTypesView = {
    id: "",
    title: "",
    price: 0,
    address: "",
    description: "",
    maxGuest: 0,
    totalBedroom: 0,
    status: "Available",
    totalBed: 0,
    totalBath: 0,
    city: "",
    propertyType: "APARTMENT",
    image: []
};