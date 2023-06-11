package com.dat.csmis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.expression.ParseException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.dat.csmis.entity.DoorLogEntity;
import com.dat.csmis.entity.DoorlogComposite;
import com.dat.csmis.entity.HolidaysEntity;
import com.dat.csmis.repository.DoorLogRepository;
import com.dat.csmis.repository.EmployeeRepository;
import com.dat.csmis.repository.HolidaysRepository;
import com.dat.csmis.repository.MenuRepository;
import com.dat.csmis.service.ImportService;
import com.dat.csmis.service.MailSer;

import net.sf.jasperreports.components.table.Row;

public class ImportServiceTest {

    @Mock
    private MenuRepository repoM;
    
    @Mock
    private EmployeeRepository repoE;
    
    @Mock
    private HolidaysRepository repoH;
    
    @Mock
    private DoorLogRepository repoD;
    
    @Mock
    private HolidaysEntity holidaysEntity;
    
    @Mock
    private DoorLogEntity doorlogEntity;

    @Mock
    private BCryptPasswordEncoder sec;
    
    @Mock
    private MailSer mailSer;


    @InjectMocks
    private ImportService importService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    public void testImportMenu_EmptyFile() throws IOException {
        // Arrange
        MultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", new byte[0]);

        // Act
        boolean result = importService.importMenu(mockMultipartFile);

        // Assert
        assertEquals(false, result);
    }

   

}
