import "@/styles/property/propertyForm.css";

const PropertyForm = () => {
  return (
    <div className="property-form">
      <div>
        <div className="title-price-container">
          <h2 className="property-input-long">Title</h2>
          <h2 className="property-price">Price</h2>
        </div>
        <p>
          Begin by giving your property an appealing title. Make it
          attention-grabbing yet informative
        </p>
        <div className="title-price-container">
          <input className="property-input-long" type="text" />
          <input className="property-price" type="text" />
        </div>
      </div>
      <div>
        <h2>Location</h2>
        <input type="text" className="property-input-long" />
      </div>
      <div>
        <h2>Description</h2>
        <textarea className="desc" />
      </div>

      <div className="property-button-container">
        <button>
          <h3>Next</h3>
        </button>
      </div>
    </div>
  );
};

export default PropertyForm;
