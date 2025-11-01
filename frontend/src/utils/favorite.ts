import api from "./axios"

export const addFavorite = async (propertyId: string) => {
    const { data } = await api.post(`/favorite/${propertyId}`);

    console.log(data)

    return data;
}