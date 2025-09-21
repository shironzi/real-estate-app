import { useState } from "react";
import { FaImage } from "react-icons/fa6";

import "@/styles/property/propertyImage.css";

const PropertyImages = () => {
  const [images, setImages] = useState<File[]>([]);

  const handleAddImage = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      const newFiles = Array.from(e.target.files);
      setImages((prev) => [...prev, ...newFiles]);
    }
  };

  const handleRemoveImage = (index: number) => {
    setImages((prev) => prev.filter((_, i) => i !== index));
  };

  const handleEditImage = (
    e: React.ChangeEvent<HTMLInputElement>,
    index: number
  ) => {
    if (e.target.files && e.target.files[0]) {
      const updatedFile = e.target.files[0];
      setImages((prev) =>
        prev.map((file, i) => (i === index ? updatedFile : file))
      );
    }
  };

  return (
    <div className="property-image-container">
      <div className="image-grid">
        {images.map((file, index) => (
          <div key={index} className="image-card">
            <img src={URL.createObjectURL(file)} alt={`preview-${index}`} />
            <div className="image-actions">
              <button onClick={() => handleRemoveImage(index)}>🗑️</button>
              <label>
                ✏️
                <input
                  type="file"
                  accept="image/*"
                  hidden
                  onChange={(e) => handleEditImage(e, index)}
                />
              </label>
            </div>
          </div>
        ))}

        <label className="add-image-card">
          <FaImage size={50} color="#fff" />
          <h4>+ Add Image</h4>
          <input
            type="file"
            multiple
            accept="image/*"
            hidden
            onChange={handleAddImage}
          />
        </label>
      </div>

      <div className="property-button-container">
        <button>
          <h3>Next</h3>
        </button>
      </div>
    </div>
  );
};

export default PropertyImages;
