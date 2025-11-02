import PropertyCard from "@/components/property/PropertyCard";
import { getProperties } from "@/utils/property";
import { useEffect, useState } from "react";
import { PropertyTypesView } from "@/pages/property/Propertytypes";
import { addFavorite, removeFavorite } from "@/utils/favorite";

const Home = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [properties, setProperties] = useState<PropertyTypesView[]>([]);

  const handleFavorite = async (propertyId: string) => {
    const isFavorite = properties.find(
      (property) => property.id === propertyId
    )?.isFavorite;

    const res = !isFavorite
      ? await addFavorite(propertyId)
      : await removeFavorite(propertyId);

    if (res.success) {
      setProperties((prev) =>
        prev.map((property) =>
          property.id === propertyId
            ? { ...property, isFavorite: !property.isFavorite }
            : property
        )
      );
    }
  };

  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const res = await getProperties();

        if (res.success) {
          setProperties(res.properties);
        }
      } catch (e: any) {
      } finally {
        setLoading(false);
      }
    };
    fetchProperties();
  }, []);

  if (loading) {
    return (
      <div>
        <h1>Loading....</h1>
      </div>
    );
  }

  return (
    <div className="bg-black">
      <div className="home-properties">
        {properties?.map((property) => (
          <PropertyCard
            key={property.id}
            propertyId={property.id}
            title={property.title}
            price={property.price}
            totalNights={1}
            image={property.image}
            address={property.address}
            status={property.status}
            isFavorite={property.isFavorite}
            isManageMode={false}
            onFavorite={handleFavorite}
          />
        ))}
      </div>

      {!properties.length && (
        <div>
          <h1>No Properties yet!</h1>
        </div>
      )}
    </div>
  );
};

export default Home;
