import PropertyCard from "@/components/property/PropertyCard";
import { getProperties } from "@/utils/property";
import { useEffect, useState } from "react";
import { PropertyTypesView } from "@/pages/property/Propertytypes";

const Home = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [properties, setProperties] = useState<PropertyTypesView[]>([]);

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
      {properties.map((property) => (
        <PropertyCard
          // isFavorite={isFavorite}
          // setIsFavorite={(favorite: boolean) => setIsFavorite(favorite)}
          title={property.title}
          price={property.price}
          totalNights={1}
          imageUrl={property.image[0]}
          propertyId={property.id}
        />
      ))}
    </div>
  );
};

export default Home;
