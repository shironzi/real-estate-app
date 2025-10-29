import api from "./axios"

export const addFavorite = async (propertyId: string) => {
    const { data } = await api.post(`/${propertyId}`);

    return data;
}