package com.woori.codenova.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tui-editor")
public class FileApiController {

	// 파일을 업로드할 디렉터리 경로
	// private final String uploadDir = Paths.get("C:", "tui-editor",
	// "upload").toString();
	@Value("${upload.path}")
	private String uploadDir;

	private final String uploadDirImages = "images";

	/**
	 * 에디터 이미지 업로드
	 * 
	 * @param image 파일 객체
	 * @retrun 업로드된 파일명
	 */
	@PostMapping("/image-upload")
	public String uploadEditorImage(@RequestParam(value = "image") final MultipartFile image) {

		System.out.format("DDDDDDDDDDDDDDDDDDDDDDDDDDDD :: ");
		if (image.isEmpty()) {
			return "";
		}

		String orgFilename = image.getOriginalFilename(); // 원본 파일명

		String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // 32자리 랜덤 문자열

		String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1); // 확장자

		String saveFilename = uuid + "." + extension; // 디스크에 저장할 파일명

		String fileFullPath = Paths.get(uploadDir + uploadDirImages, saveFilename).toString(); // 디스크에 저장할 파일의 전체 경로

		System.out.format("fileFullPath={}%s\n", fileFullPath);

		// uploadDir에 해당되는 디렉터리가 없으면, uploadDir에 포함되는 전체 디렉터리 생성
		File dir = new File(uploadDir + uploadDirImages);

		System.out.format("DDDDDDDDDDD :: 디렉토리 :: DDDDDDDDDDDDDDDDD :: " + dir.toString());

		if (dir.exists() == false) {

			System.out.format("\nDDDDDDDDDDD :: 디렉토리 없지 :: DDDDDDDDDDDDDDDDD :: ");

			if (dir.mkdirs()) {
				System.out.println("디렉토리 생성 성공: " + dir.getAbsolutePath());
			} else {
				System.out.println("디렉토리 생성 실패 또는 이미 존재");
			}

		}

		System.out.format("\nDDDDDDDDDDD :: 디렉토리 있지 :: DDDDDDDDDDDDDDDDD :: " + fileFullPath);
		try {
			// 파일 저장 (write to disk)
			File uploadFile = new File(fileFullPath);
			image.transferTo(uploadFile);
			return saveFilename;

		} catch (IOException e) {
			// 예외 처리는 따로 해주는 게 좋습니다.
			throw new RuntimeException(e);
		}
	}

	/**
	 * 디스크에 업로드된 파일을 byte[]로 반환
	 * 
	 * @param filename 디스크에 업로드된 파일명
	 * @return image byte array
	 */
	@GetMapping(value = "/image-print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public byte[] printEditorImage(@RequestParam(value = "filename") final String filename) {
		// 업로드된 파일의 전체 경로
		String fileFullPath = Paths.get(uploadDir + uploadDirImages, filename).toString();

		// 파일이 없는 경우 예외 throw
		File uploadedFile = new File(fileFullPath);

		if (uploadedFile.exists() == false) {

			System.out.format("\nDDDDDDDDDDD :: 파일이 없고 ::  DDDDDDDDDDDDDDDDD :: ");

			throw new RuntimeException();
		}

		try {
			// 이미지 파일을 byte[]로 변환 후 반환
			byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());

			System.out.format("\nDDDDDDDDDDD :: 파일이 있고 ::  DDDDDDDDDDDDDDDDD ::");

			return imageBytes;

		} catch (IOException e) {
			// 예외 처리는 따로 해주는 게 좋습니다.
			throw new RuntimeException(e);
		}
	}

}
