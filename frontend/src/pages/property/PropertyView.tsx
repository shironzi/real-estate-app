import { getPropertyById } from "@/utils/property";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  PropertyTypesView,
  PropertyTypesViewDefaultData,
} from "@/pages/property/Propertytypes";

import "@/styles/property/viewProperty.css";

const PropertyView = () => {
  const { id } = useParams();

  const [loading, setLoading] = useState<boolean>(true);
  const [property, setProperty] = useState<PropertyTypesView>(
    PropertyTypesViewDefaultData
  );

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        if (!id) {
          throw new Error("Property ID is missing in search params");
        }

        const res = await getPropertyById(id);

        console.log(res);

        if (res.success) {
          setProperty(res.property);
        }
      } catch (e: any) {
      } finally {
        setLoading(false);
      }
    };

    fetchProperty();
  }, [id]);

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
        {property.image.map((image: string, index: number) => (
          <img src={image} key={index} alt={`property-image-${image}`} />
        ))}
      </div>

      <h3>
        {property.title}, {property.address}
      </h3>
      <ul className="view-amenities">
        <li>
          <h4>
            {property.maxGuest} {property.maxGuest > 1 ? "guests" : "guest"}
          </h4>
        </li>
        <li>
          <h4>
            {property.totalBedroom}{" "}
            {property.totalBedroom > 1 ? "bedrooms" : "bedroom"}
          </h4>
        </li>
        <li>
          <h4>
            {property.totalBed} {property.totalBed > 1 ? "beds" : "bed"}
          </h4>
        </li>
        <li>
          <h4>
            {property.totalBath} {property.totalBath > 1 ? "bath" : "baths"}
          </h4>
        </li>
      </ul>

      <p>{property.description}</p>
    </div>
  );
};

export default PropertyView;
