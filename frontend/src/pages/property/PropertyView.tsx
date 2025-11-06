import { getPropertyById } from "@/utils/property";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  PropertyTypesView,
  PropertyTypesViewDefaultData,
} from "@/pages/property/Propertytypes";

import "@/styles/property/viewProperty.css";
import PropertySlider from "@/components/property/PropertySlider";
import { GoPeople } from "react-icons/go";
import { LuBedSingle } from "react-icons/lu";
import { MdOutlineBathroom, MdOutlineMeetingRoom } from "react-icons/md";

const PropertyView = () => {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [property, setProperty] = useState<PropertyTypesView>(
    PropertyTypesViewDefaultData
  );

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        if (!id) throw new Error("Missing property ID");
        const res = await getPropertyById(id);
        if (res.success) setProperty(res.property);
      } catch (e) {
        console.error("Failed to fetch property:", e);
      } finally {
        setLoading(false);
      }
    };
    fetchProperty();
  }, [id]);

  if (loading) {
    return (
      <div className="property-view-loading">
        <h2>Loading property details...</h2>
      </div>
    );
  }

  return (
    <div className="property-view-container">
      <div className="property-view-grid">
        {/* LEFT: Image Slider */}
        <div className="property-slider-wrapper">
          <PropertySlider images={property.image} />
        </div>

        {/* RIGHT: Details */}
        <div className="property-details">
          <h2 className="property-title">{property.title}</h2>
          <p className="property-address">{property.address}</p>

          <p className="property-description">{property.description}</p>

          <ul className="view-amenities">
            <li>
              <GoPeople size={28} />
              <span>
                {property.maxGuest} {property.maxGuest > 1 ? "guests" : "guest"}
              </span>
            </li>
            <li>
              <MdOutlineMeetingRoom size={28} />
              <span>
                {property.totalBedroom}{" "}
                {property.totalBedroom > 1 ? "bedrooms" : "bedroom"}
              </span>
            </li>
            <li>
              <LuBedSingle size={28} />
              <span>
                {property.totalBed} {property.totalBed > 1 ? "beds" : "bed"}
              </span>
            </li>
            <li>
              <MdOutlineBathroom size={28} />
              <span>
                {property.totalBath} {property.totalBath > 1 ? "baths" : "bath"}
              </span>
            </li>
          </ul>

          <button className="rent-btn">Rent this property</button>
        </div>
      </div>
    </div>
  );
};

export default PropertyView;
