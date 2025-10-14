import { getPropertyById } from "@/utils/property";
import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import {
  PropertyTypesView,
  PropertyTypesViewDefaultData,
} from "@/pages/property/Propertytypes";

const PropertyView = () => {
  const [searchParams] = useSearchParams();

  const [loading, setLoading] = useState<boolean>(true);
  const [property, setProperty] = useState<PropertyTypesView>(
    PropertyTypesViewDefaultData
  );

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const propertyId = searchParams.get("id");

        if (!propertyId) {
          throw new Error("Property ID is missing in search params");
        }

        const res = await getPropertyById(propertyId);

        if (res.success) {
          setProperty(res.property);
        }
      } catch (e: any) {
      } finally {
        setLoading(false);
      }
    };

    fetchProperty();
  }, [searchParams]);

  if (loading) {
    return (
      <div>
        <h1>Loading...</h1>
      </div>
    );
  }

  return (
    <div>
      <h2>{property.title}</h2>
      <div>
        {property.image.map((image: string) => (
          <img src={image} />
        ))}
      </div>

      <h3>{property.address}</h3>
      <div>
        <h4>
          {property.maxGuest} {property.maxGuest > 1 ? "guests" : "guest"}
        </h4>
        <h4>
          {property.totalBedroom}{" "}
          {property.totalBedroom > 1 ? "bedrooms" : "bedroom"}
        </h4>
        <h4>
          {property.totalBed} {property.totalBed > 1 ? "beds" : "bed"}
        </h4>
        <h4>
          {property.totalBath} {property.totalBath > 1 ? "bath" : "baths"}
        </h4>
      </div>

      <p>{property.description}</p>
    </div>
  );
};

export default PropertyView;
