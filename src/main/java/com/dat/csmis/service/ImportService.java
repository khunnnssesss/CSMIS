package com.dat.csmis.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dat.csmis.entity.DoorLogEntity;
import com.dat.csmis.entity.DoorlogComposite;
import com.dat.csmis.entity.EmployeeEntity;
import com.dat.csmis.entity.HolidaysEntity;
import com.dat.csmis.entity.MenuEntity;
import com.dat.csmis.entity.YourEntityKey;
import com.dat.csmis.repository.DoorLogRepository;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.repository.HolidaysRepository;
import com.dat.csmis.repository.MenuRepository;
import com.dat.csmis.security.SecurityConfig;

@Service
public class ImportService {
	
	@Autowired
	MenuRepository repoM;
	@Autowired
	EmployeeRepository repoE;
	@Autowired
	HolidaysRepository repoH;
	@Autowired
	DoorLogRepository repoD;
	@Autowired
	SecurityConfig sec;
	@Autowired MailSer mailSer;
	
	public boolean importMenu(MultipartFile part) {
		String fileName=null;	
		if(!part.isEmpty()) {		
			
			try {
				 fileName = StringUtils.cleanPath(part.getOriginalFilename());
				 String system=System.getProperty("user.dir").replace("\\" , "/");
				 String uploadPath=system+"/src/main/resources/static/file/" +fileName;
			     File file = new File(uploadPath);
			     part.transferTo(file);
			     
			     
				 System.out.println("Name of PDF : "+fileName);
			     String outputImage = system+"/src/main/resources/static/images/output.png";
			     System.out.println(outputImage);
				 String inputPdf=system+"/src/main/resources/static/file/"+fileName;
			       
			     PDDocument document = PDDocument.load(new File(inputPdf));

			     PDFRenderer renderer = new PDFRenderer(document);
			     BufferedImage image = renderer.renderImage(0);

			     File outputFile = new File(outputImage);
			     boolean a=ImageIO.write(image, "png", outputFile);
			     if(a) {
			        System.out.println("PDF to IMG successful!!!");
			     }else {
			        System.out.println("PDF to IMG failed!!!");
			     }
			     document.close();
			  
			}catch(Exception e) {
				System.out.println("PDF upload error "+e.getMessage());
				return false;
			}
			
			
			MenuEntity entity=new MenuEntity();
			entity.setMenu(fileName);
			entity.setStatus("InActive");
			repoM.save(entity);
			int a=(int) repoM.count();
			repoM.updateA("Active", a);
			repoM.updateB("InActive", a);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean importEmployee(MultipartFile file){
		boolean isFirstRow= true;
		List<EmployeeEntity> newExcel = new ArrayList<EmployeeEntity>();
		BCryptPasswordEncoder encode= sec.encoder();
		Workbook wb;
		String StaffId, name, email, team, role, dept, division, status;
		int doorLog, password;
		try {
			wb = WorkbookFactory.create(file.getInputStream());
		
		System.out.println("Workbook has " + wb.getNumberOfSheets() + " Sheets : ");
        for(Sheet sheet: wb) {
            System.out.println("=> " + sheet.getSheetName());
            for (Row row: sheet) {
            	
            	if(isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
            	
            		 StaffId = row.getCell(0).getStringCellValue();
                	 doorLog= (int) row.getCell(1).getNumericCellValue();
                	 name = row.getCell(2).getStringCellValue();
                	 email= row.getCell(3).getStringCellValue();
                	 password= (int) row.getCell(4).getNumericCellValue();
                	 team= row.getCell(5).getStringCellValue();
                	 role= row.getCell(6).getStringCellValue();
                	 dept= row.getCell(7).getStringCellValue();
                	 division= row.getCell(8).getStringCellValue();
                	 status= row.getCell(9).getStringCellValue();
            	
            	
            	
            	System.out.println(StaffId);
        		EmployeeEntity entity = new EmployeeEntity();
        		entity.setStaffId(StaffId);
            	entity.setDoorLog(String.valueOf(doorLog));
            	entity.setName(name);
            	entity.setEmail(email);
            	entity.setPassword(encode.encode(String.valueOf(password)));
            	entity.setTeam(team);
            	entity.setRole(role);
            	entity.setDept(dept);
            	entity.setDivision(division);
            	entity.setStatus(status);
            	entity.setPhoto("avatar.png");
            	newExcel.add(entity);
            	Optional<EmployeeEntity> emp=repoE.findByStaffId(String.valueOf(StaffId));
            	
            	if(emp.isPresent()) {         		            		
            		EmployeeEntity ee=emp.get();
            		 try {
                		repoE.update(entity.getStaffId(),entity.getDoorLog(),entity.getEmail(),entity.getTeam()
                				,entity.getRole(),entity.getDept(),
                					entity.getDivision(),entity.getStatus(),ee.getId());
                	 }catch(Exception e) {
                		System.out.println("error on 1 is "+e.getMessage());
                		
                		return false;
                	}
            		
            	}
            	else{      	
                	try {
                		repoE.save(entity);
						mailSer.sendEmail(entity.getEmail(), entity.getName());
                	}catch(Exception e) {
                		System.out.println("error on 2 is "+e.getMessage());
                		return false;
                	}
            	}	
            }
        }
        } catch (NullPointerException e) {
        	System.out.println("error on null is "+e.getMessage());
		} catch (ExceptionInInitializerError e) {
			System.out.println("error on 4 is "+e.getMessage());
			return false;
		}catch (Exception e) {
        	System.out.println("error on 3 is "+e.getMessage());
			return false;
		}
		
		
		List<EmployeeEntity> oldEmployeeList = repoE.findAll();
		for(EmployeeEntity ee:oldEmployeeList) {
			System.out.println("Old emp ==> "+ee.getEmail());
		}
		if(!oldEmployeeList.isEmpty() && !newExcel.isEmpty()) {
			for (EmployeeEntity oldEmployee : oldEmployeeList) {
			    boolean found = false;
			    for (EmployeeEntity newEmployee : newExcel) {
			        if (oldEmployee.getStaffId().equals(newEmployee.getStaffId())) {
			            found = true;
			            break;
			        }
			    }
			    
			    if (!found) {
			    	System.out.println("Change InActive to "+ oldEmployee.getEmail());
			        oldEmployee.setStatus("InActive");
			        repoE.save(oldEmployee);
			    }
			}
		}
				
        return true;
	}
	
	public boolean importHoliday(MultipartFile file){
		boolean isFirstRow= true;
		Workbook wb;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
		try {
			wb = WorkbookFactory.create(file.getInputStream());
		
        for(Sheet sheet: wb) {
            for (Row row: sheet) {
            	
            	if(isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
            	
            	String date= row.getCell(0).getStringCellValue();
            	String days=row.getCell(1).getStringCellValue();
            	
            	
            	date = date.replaceAll("(\\d)(?:st|nd|rd|th)", "$1");
        		LocalDate localDate = null;
            	String[] substring = date.split(", ");
            	HolidaysEntity entity = new HolidaysEntity();
            	for(var i=0; i< substring.length; i++) {
            		String inputString = substring[0];
            		String[] datedata=inputString.split(", ");
            		String dateSringWithYear = datedata[0]+ " " + LocalDate.now().getYear();
            		System.out.println("DateStringWithYear "+dateSringWithYear);
            		localDate = LocalDate.parse(dateSringWithYear, formatter);
                    System.out.println("Date is ==> "+ localDate);
                    

            	}
            	
            	
            	
            	Optional<HolidaysEntity> he= repoH.findByDate(String.valueOf(localDate));
            	entity.setDate(String.valueOf(localDate));
        		entity.setHolidays(days);
            	if(he.isPresent()) {
            		HolidaysEntity ho=he.get();
            		repoH.updateHoli(entity.getDate(), entity.getHolidays(), ho.getId());
            		System.out.println("-----------------------------------------");
            		System.out.println("IN ELSE CONDITION!");
            	}else {
            		
                	
            		repoH.save(entity);
            	}
            		
            }
        }
        } catch (Exception e) {
        	System.out.println("error is "+e.getMessage());
			return false;
        }
		
			
        return true;
	}
	public boolean importDoorlog(MultipartFile file){
		boolean isFirstRow= true;
		Workbook wb;
		
			try {
				wb = WorkbookFactory.create(file.getInputStream());
			
		
        for(Sheet sheet: wb) {
            for (Row row: sheet) {
            	
            	if(isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
            	
            	String staffId = row.getCell(0).getStringCellValue();
            	String name = row.getCell(1).getStringCellValue();
            	Date date= row.getCell(2).getDateCellValue();
            	int doorLog= (int) row.getCell(3).getNumericCellValue();
            	String dept = row.getCell(4).getStringCellValue();
            	
            	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            	String dateString = df.format(date);
            	DoorlogComposite com = new DoorlogComposite();
            	com.setDate(dateString);
            	com.setDoorLog(String.valueOf(doorLog));
            	DoorLogEntity entity = new DoorLogEntity();
            	entity.setKey(com);
        		entity.setStaffId(staffId);
        		entity.setName(name);
        		entity.setDept(dept);         	
            	repoD.save(entity);	
            }
        }
        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return false;
			}
			
        return true;
	}

}

