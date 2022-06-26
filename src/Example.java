/*
 * NAME: Muhammed  Fawaz Sharif
 * STUDENT NUMBER: 690373
 * MODULE: CS-255 Computer Graphics.
 * I declare that this is my own work. 
 * */
import javafx.application.Application;
import javafx.beans.value.ChangeListener; 
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.PixelWriter;
import javafx.stage.Stage;
import java.io.*;

public class Example extends Application {
	short cthead[][][]; //store the 3D volume data set
	short min, max; //min/max value in the 3D volume data set
	
	   // The writableImage for each image view 
			WritableImage medical_image1 = new WritableImage(256, 256);
			WritableImage medical_image2 = new WritableImage(256, 256);
			WritableImage medical_image3 = new WritableImage(256, 256);
			
		/* Writable Image for each slice of the CTHead,
		 * This will be used to create the thumbnails 
		 * */	
			WritableImage medical_image4 = new WritableImage(256, 256);
			
			 Slider resize_sliderz = new Slider(1, 600, 0);
			 Slider resize_slidery = new Slider(1, 600, 0);
			 Slider resize_sliderx = new Slider(1, 600, 0);
			 
			// ImageView of each image  
				ImageView imageView1 = new ImageView(medical_image1); 
				ImageView imageView2 = new ImageView(medical_image2); 
				ImageView imageView3 = new ImageView(medical_image3);
				ImageView imageView4 = new ImageView(medical_image4);
	
	@Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
		stage.setTitle("CThead Viewer");
		
		ReadData();
		
		
		Button mip_button=new Button("MIP"); //an example button to switch to MIP mode
		//sliders to step through the slices (z and y directions) (remember 113 slices in z direction 0-112)
		Slider zslider = new Slider(0, 112, 0);
		Slider yslider = new Slider(0, 255, 0);
		Slider xslider = new Slider(0, 255, 0);
		
		mip_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	MIP_TOP(medical_image1);
            	MIP_SIDE(medical_image2);
				MIP_FRONT(medical_image3);
								
            }
        });
			
		zslider.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed (ObservableValue <? extends Number>
					observable, Number oldValue, Number newValue) 
					{
						MIP_TOP(medical_image1, newValue.intValue());
						System.out.println(newValue.intValue());
					}
				});
		
		yslider.valueProperty().addListener( 
	            new ChangeListener<Number>() { 
					public void changed(ObservableValue <? extends Number >  
						observable, Number oldValue, Number newValue) 
	            { 
					MIP_SIDE(medical_image2, newValue.intValue());	
	                System.out.println(newValue.intValue());
	            } 
	        }); 
		
		xslider.valueProperty().addListener( 
	            new ChangeListener<Number>() { 
					public void changed(ObservableValue <? extends Number >  
						observable, Number oldValue, Number newValue) 
	            { 
					MIP_FRONT(medical_image3, newValue.intValue());	
	                System.out.println(newValue.intValue());
	            } 
	        }); 
				
		resize_sliderz.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed (ObservableValue <? extends Number>
					observable, Number oldValue, Number newValue) 
					{
						RESIZE_IMG_TOP(newValue.intValue(), newValue.intValue()); 
						System.out.println(newValue.intValue());
					}
				});
		    		
		resize_slidery.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed (ObservableValue <? extends Number>
					observable, Number oldValue, Number newValue) 
					{
						
						RESIZE_IMG_SIDE(newValue.intValue(), newValue.intValue()); 
						System.out.println(newValue.intValue());
					}
				});
		resize_sliderx.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed (ObservableValue <? extends Number>
					observable, Number oldValue, Number newValue) 
					{
						
						RESIZE_IMG_FRONT(newValue.intValue(), newValue.intValue()); 
						System.out.println(newValue.intValue());
					}
				});
		
	      Button thumbnail_button = new Button("THUMBNAILS");
			
			thumbnail_button.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {   	
	            	FlowPane fPane = new FlowPane (THUMBNAILS_RESIZED());	            	
	            	Scene scene = new Scene(fPane, 1040, 1000);
	                stage.setScene(scene);
	                stage.show();
	                
	            }
	        });
			
//Ref: https://www.tutorialspoint.com/javafx/layout_gridpane.htm		
			
	      GridPane gridPane = new GridPane(); 
	            
	      //Setting size for the pane  
	      gridPane.setMinSize(800, 800); 
	       
	      //Setting the padding  
	      gridPane.setPadding(new Insets(10, 10, 10, 10)); 
	      
	      //Setting the vertical and horizontal gaps between the columns 
	      gridPane.setVgap(10); 
	      gridPane.setHgap(10);       
	      
	      //Setting the Grid alignment 
	      gridPane.setAlignment(Pos.CENTER); 
	       
	      //Arranging all the nodes in the grid 
	      gridPane.add(imageView1, 0, 0);	      
	      gridPane.add(zslider, 1, 1);
	      gridPane.add(resize_sliderz, 0, 2);
	      gridPane.add(imageView2, 0, 3);
	      gridPane.add(yslider, 1, 4);
	      gridPane.add(resize_slidery, 0, 5);
	      gridPane.add(imageView3, 0, 6);        
	      gridPane.add(xslider, 1, 7);
	      gridPane.add(resize_sliderx, 0, 8);
	      gridPane.add(mip_button, 1, 9);
	      gridPane.add(thumbnail_button, 2 , 9);
	     // gridPane.add(reset_button, 3, 9);
	      	         
//https://examples.javacodegeeks.com/desktop-java/javafx/scene/image-scene/javafx-image-example/

        Scene scene = new Scene(gridPane, 1040, 1000);
        stage.setScene(scene);
        stage.show();
    }
			
	//Function to read in the cthead data set
	public void ReadData() throws IOException {
		//File name is hardcoded here - much nicer to have a dialog to select it and capture the size from the user
		File file = new File("C:\\Users\\falli\\eclipse-workspace\\Graphics\\CThead.raw");
		//Read the data quickly via a buffer (in C++ you can just do a single fread - I couldn't find if there is an equivalent in Java)
		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
		
		int i, j, k; //loop through the 3D data set
		
		min=Short.MAX_VALUE; max=Short.MIN_VALUE; //set to extreme values
		short read; //value read in
		int b1, b2; //data is wrong Endian (check wikipedia) for Java so we need to swap the bytes around
		
		cthead = new short[113][256][256]; //allocate the memory - note this is fixed for this data set
		//loop through the data reading it in
		for (k=0; k<113; k++) {
			for (j=0; j<256; j++) {
				for (i=0; i<256; i++) {
					//because the Endianess is wrong, it needs to be read byte at a time and swapped
					b1=((int)in.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types
					b2=((int)in.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types
					read=(short)((b2<<8) | b1); //and swizzle the bytes around
					if (read<min) min=read; //update the minimum
					if (read>max) max=read; //update the maximum
					cthead[k][j][i]=read; //put the short into memory (in C++ you can replace all this code with one fread)
				}
			}
		}
		System.out.println(min+" "+max); //diagnostic - for CThead this should be -1117, 2248
		//(i.e. there are 3366 levels of grey (we are trying to display on 256 levels of grey)
		//therefore histogram equalization would be a good thing
	}

	 /*
        This function shows how to carry out an operation on an image.
        It obtains the dimensions of the image, and then loops through
        the image carrying out the copying of a slice of data into the
		image.
    */
	
	// This is the top view of the image 
    public void MIP_TOP(WritableImage image) {
            //Get image dimensions, and declare loop variables
            int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
            PixelWriter image_writer = image.getPixelWriter();
            
			float col;
			short datum;
            //Shows how to loop through each pixel and colour
            //Try to always use j for loops in y, and i for loops in x
            //as this makes the code more readable
			
            for (j=0; j<h; j++) {
                    for (i=0; i<h; i++) {
						
							short maxIn = Short.MIN_VALUE;
							for(k= 0; k<113;k++) {
								datum=cthead [k][j][i];
								if(datum > maxIn) {
									 maxIn = datum;	
								}
							col=(((float)maxIn-(float)min)/((float)(max-min)));
                            for (c=0; c<3; c++) {
								//and now we are looping through the bgr components of the pixel
								//set the colour component c of pixel (i,j)
								image_writer.setColor(i, j, Color.color(col,col,col, 1.0));
			//					data[c+3*i+3*j*w]=(byte) col;
                            } //
                            image_writer.setColor(i, j, Color.color(col,col,col, 1.0));//colour loop
                    } // column loop
            } // row loop
                    
            
         }
            
    }
    
    /*
     * This function MIP_TOP function to get the slider working.
     * */
    public void MIP_TOP(WritableImage image, int newValue) {
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        
		float col;
		short datum;
        for (j=0; j<256; j++) {
                for (i=0; i<256; i++) {
						datum=cthead[(int)(newValue)][j][i]; //get values from slice 76 (change this in your assignment)
						//calculate the colour by performing a mapping from [min,max] -> [0,255]
						col=(((float)datum-(float)min)/((float)(max-min)));
                        for (c=0; c<3; c++) {
							
				    	image_writer.setColor(i, j, Color.color(col,col,col, 1.0));
	
                        } 
                } 
        } 
    }
   
    //This is the front view of the image 
    public void MIP_SIDE(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        
		float col;
		short datum = 0;
       
		
		for (k=0; k<113; k++) {
		      for (j=0; j<h; j++) {
							
								short maxIn = Short.MIN_VALUE;
								for(i= 0; i<w;i++) {
									datum = cthead [k][j][i];
									if(datum > maxIn) {
										 maxIn = datum;	 
									}
					col=(((float)maxIn-(float)min)/((float)(max-min)));
                    for (c=0; c<3; c++) {
						//and now we are looping through the bgr components of the pixel
						//set the colour component c of pixel (i,j)
						image_writer.setColor(j, k, Color.color(col,col,col, 1.0));
	//					data[c+3*i+3*j*w]=(byte) col;
                    } // colour loop
            } // column loop
    } // row loop
            
	}
}
    
    /*
     * This function MIP_SIDE function to get the slider working.
     * */
    public void MIP_SIDE(WritableImage image, int newValue) {
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        
		float col;
		short datum;
        for (j=0; j<113; j++) {
                for (i=0; i<256; i++) {
						datum=cthead[j][i][newValue];
						col=(((float)datum-(float)min)/((float)(max-min)));
                        for (c=0; c<3; c++) {
							image_writer.setColor(i, j, Color.color(col,col,col, 1.0));
                        } 
                } 
        } 
                
        
    }
   
    // This is the side view of the image 
    public void MIP_FRONT(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        
		float col;
		short datum;
       
        for (k=0; k<113; k++) {
                for (i=0; i<w; i++) {
					
						short maxIn = Short.MIN_VALUE;
						for(j= 0; j<h;j++) {
							datum=cthead [k][j][i];
							if(datum > maxIn) {
								 maxIn = datum;	
							}
						col=(((float)maxIn-(float)min)/((float)(max-min)));
                        for (c=0; c<3; c++) {
							
							image_writer.setColor(i, k, Color.color(col,col,col, 1.0));
							
                        } // colour loop
                } // column loop
        } // row loop
                
        }
        
    }
    
    /*
     * This function MIP_SIDE function to get the slider working.
     * */
    public void MIP_FRONT(WritableImage image, int newValue) {
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c, k;
        PixelWriter image_writer = image.getPixelWriter();
        
		float col;
		short datum;
        for (j=0; j<113; j++) {
                for (i=0; i<256; i++) {
						datum=cthead[j][(int)(newValue)][i];
						col=(((float)datum-(float)min)/((float)(max-min)));
                        for (c=0; c<3; c++) {
							image_writer.setColor(i, j, Color.color(col,col,col, 1.0));
                        } 
                } 
        } 
                
        
    }
    
    /*for j=0 to Yb-1 
     * for i=0 to Xb-1 
     * for c=0 to 2 
     * y=j*Ya/Yb <- make sure this is done using 
     * floats x=i*Xa/Xb <- same
     *  Ib[j][i][c]=Ia[y][x][c] 
     * */
    /*
     * This Function resizes the top view of the image
     * */
    public void RESIZE_IMG_TOP(int xb, int yb) {
    	 WritableImage ib = new WritableImage(xb, yb); 
    	 int xa=(int) medical_image1.getWidth(), ya=(int) medical_image1.getHeight(), i, j, c;
    	
         PixelReader image_reader = medical_image1.getPixelReader();
   	  	 PixelWriter image_writer = ib.getPixelWriter();
    	  
    	  System.out.println("Resizing...");
                
          for (j = 0; j < yb-1; j++) {
                  for (i = 0; i < xb-1; i++) {
                	  for(c = 0; c < 2; c++) {
                	  float y = j *  (float)ya/(float) yb;
                	  float x = i * (float)xa/(float)  xb;
                	  
                	  Color color = image_reader.getColor( (int)y, (int)x);
                	  image_writer.setColor(j,i,color);
                	  
                	  }                        
                 }        
          }
		imageView1.setImage(ib);
   }
    
    /*
     * This Function resizes the side view of the image
     * */
    public void RESIZE_IMG_SIDE(int xb, int yb) {
   	 WritableImage ib = new WritableImage(xb, yb); 
   	 int xa=(int) medical_image2.getWidth(), ya=(int) medical_image2.getHeight(), i, j, c;
      
   	 PixelReader image_reader = medical_image2.getPixelReader();
  	 PixelWriter image_writer = ib.getPixelWriter();
   	    
   	  System.out.println("Resizing...");
               
         for (j = 0; j < yb-1; j++) {
                 for (i = 0; i < xb-1; i++) {
               	  for(c = 0; c < 2; c++) {
               	  float y = j *  (float)ya/(float) yb;
               	  float x = i * (float)xa/(float)  xb;
               	  
               	  Color color = image_reader.getColor( (int)y, (int)x);
               	  image_writer.setColor(j,i,color);
               	  
               	  }                        
                }        
         }
		imageView2.setImage(ib);
  }
    
    /*
     * This Function resizes the front view of the image
     * */
    public void RESIZE_IMG_FRONT(int xb, int yb) {
   	 WritableImage ib = new WritableImage(xb, yb); 
   	 int xa=(int) medical_image3.getWidth(), ya=(int) medical_image3.getHeight(), i, j, c;
    
   	 PixelReader image_reader = medical_image3.getPixelReader();
  	 PixelWriter image_writer = ib.getPixelWriter();
   	 
   	  System.out.println("Resizing...");
               
         for (j = 0; j < yb-1; j++) {
                 for (i = 0; i < xb-1; i++) {
               	  for(c = 0; c < 2; c++) {
               	  float y = j *  (float)ya/(float) yb;
               	  float x = i * (float)xa/(float)  xb;
               	  
               	  Color color = image_reader.getColor( (int)y, (int)x);
               	  image_writer.setColor(j,i,color);
               	  
               	  }                        
                }        
         }
		imageView3.setImage(ib);
  }
   
    /*
     * This function displays the thumbnail of a slice
     * */
    public WritableImage THUMBNAILS(WritableImage image, int slice) {
    	
        int w=(int) image.getWidth(), h=(int) image.getHeight(), i, j, c;
        PixelWriter image_writer = image.getPixelWriter();
        
		float col;
		short datum;
		
        for (j=0; j<256; j++) {
                for (i=0; i<256; i++) {
                	
						datum=cthead[slice][j][i]; 
						//get values from slice 76 (change this in your assignment)
						//calculate the colour by performing a mapping from [min,max] -> [0,255]
						col=(((float)datum-(float)min)/((float)(max-min)));
						
                        for (c=0; c<3; c++) {
							
				    	image_writer.setColor(i, j, Color.color(col,col,col, 1.0));
				    	
                      
                       
                        } 
                }
        }	    
		return image; 
    }
    
 public ImageView[] THUMBNAILS_RESIZED() {
	 
	 WritableImage[] thumbnailsImages = new WritableImage[113];
     ImageView[] thumbnailsView = new ImageView[112];
       int w = 256, h = 256,  i, j, c;
	   float col;
	   short datum;
	  
	   //Maps the thumbnail and ImageView in the array accordingly.
	   for(int l = 0; l< thumbnailsView.length; l++) {
			thumbnailsView[l] = new ImageView(thumbnailsImages[l]);
	   }
	 
	 
	   for(int slices = 0; slices < thumbnailsView.length; slices++) {
		   thumbnailsImages[slices] = new WritableImage(w,h);
		   PixelWriter image_writer;
		   image_writer = thumbnailsImages[slices].getPixelWriter();
		   
        for (j=0; j<256; j++) {
                for (i=0; i<256; i++) {
                	
						datum=cthead[slices][j][i]; 
						col=(((float)datum-(float)min)/((float)(max-min)));
                        for (c=0; c<3; c++) {	
				    	image_writer.setColor(i, j, Color.color(col,col,col, 1.0));
				    	
                        }
                        
                }
                
        }
        thumbnailsView[slices].setImage(RESIZE_IMG_THUMBNAIL(THUMBNAILS(medical_image4, slices), 100, 100));
       
        for(ImageView  iv : thumbnailsView) {
       
        	iv.addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
	          public void handle(MouseEvent event) {
	        	
	        		  iv.setImage(RESIZE_IMG_THUMBNAIL(medical_image4, 256, 256));	  
	        }
	    });
        	
        }
        
	  }
        
	   return thumbnailsView; 
    }
 
 
 
 public WritableImage RESIZE_IMG_THUMBNAIL(WritableImage ia, int xb, int yb) {
      	 WritableImage ib = new WritableImage(xb, yb); 
      	 int xa=(int) ia.getWidth(), ya=(int) ia.getHeight(), i, j, c;
       
      	 PixelReader image_reader = ia.getPixelReader();
     	 PixelWriter image_writer = ib.getPixelWriter();
         
            for (j = 0; j < yb-1; j++) {
                    for (i = 0; i < xb-1; i++) {
                  	  for(c = 0; c < 2; c++) {
                  	  float y = j *  (float)ya/(float) yb;
                  	  float x = i * (float)xa/(float)  xb;
                  	  
                  	  Color color = image_reader.getColor( (int)y, (int)x);
                  	  image_writer.setColor(j,i,color);
                  	  
                  	  }                        
                   }        
            }
   		return ib;
     }
     
    public static void main(String[] args) {
        launch();
           
    }

}