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
            isFavorite={false}
            isManageMode={false}
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
