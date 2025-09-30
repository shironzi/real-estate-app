import { PropertyTypes } from "@/context/PropertyContext";
import api from "./axios";

export const createProperty = async (info: PropertyTypes) => {

    try {
        const formData = new FormData();
        formData.append("title", info.title);
        formData.append("description", info.description);
        formData.append("price", info.price.toString())
        formData.append("maxGuest", info.maxGuest.toString());
        formData.append("totalBedroom", info.totalBedroom.toString());
        formData.append("totalBed", info.totalBed.toString());
        formData.append("totalBath", info.totalBath.toString());
        formData.append("city", info.city)
        formData.append("propertyType", info.propertyType);
        formData.append("address", info.address)

        if (info.images && info.images.length > 0) {
            info.images.forEach((image) => {
                formData.append("images", image);
            });
        }

        const res = await api.post("/property", formData);

        return res.data;
    } catch (e: any) {
        throw new Error("Something went wrong. Please try again.");
    }

}