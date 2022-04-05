/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.http.converter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
//NO_ANDROID import javax.activation.FileTypeMap;
//NO_ANDROID import javax.activation.MimetypesFileTypeMap;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 * Implementation of {@link HttpMessageConverter} that can read and write {@link Resource Resources}.
 *
 * <p>By default, this converter can read all media types. The Java Activation Framework (JAF) - if available - is used
 * to determine the {@code Content-Type} of written resources. If JAF is not available, {@code application/octet-stream}
 * is used.
 *
 * @author Arjen Poutsma
 * @since 3.0.2
 */
public class ResourceHttpMessageConverter implements HttpMessageConverter<Resource> {

	private static final boolean jafPresent =
			ClassUtils.isPresent("javax.activation.FileTypeMap", ResourceHttpMessageConverter.class.getClassLoader());

	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return Resource.class.isAssignableFrom(clazz);
	}

	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return Resource.class.isAssignableFrom(clazz);
	}

	public List<MediaType> getSupportedMediaTypes() {
		return Collections.singletonList(MediaType.ALL);
	}

	public Resource read(Class<? extends Resource> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		byte[] body = FileCopyUtils.copyToByteArray(inputMessage.getBody());
		return new ByteArrayResource(body);
	}

	public void write(Resource resource, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		HttpHeaders headers = outputMessage.getHeaders();
		if (contentType == null || contentType.isWildcardType() || contentType.isWildcardSubtype()) {
			contentType = getContentType(resource);
		}
		if (contentType != null) {
			headers.setContentType(contentType);
		}
		Long contentLength = getContentLength(resource, contentType);
		if (contentLength != null) {
			headers.setContentLength(contentLength);
		}
		FileCopyUtils.copy(resource.getInputStream(), outputMessage.getBody());
		outputMessage.getBody().flush();
	}

	private MediaType getContentType(Resource resource) {
		/* NO_ANDROID
		if (jafPresent) {
			return ActivationMediaTypeFactory.getMediaType(resource);
		}
		else {
			return MediaType.APPLICATION_OCTET_STREAM;
		}*/
		return MediaType.APPLICATION_OCTET_STREAM;
	}

	protected Long getContentLength(Resource resource, MediaType contentType) throws IOException {
		return resource.contentLength();
	}


	/**
	 * Inner class to avoid hard-coded JAF dependency.
	 */
	/* NO_ANDROID
	private static class ActivationMediaTypeFactory {

		private static final FileTypeMap fileTypeMap;

		static {
			fileTypeMap = loadFileTypeMapFromContextSupportModule();
		}

		private static FileTypeMap loadFileTypeMapFromContextSupportModule() {
			// see if we can find the extended mime.types from the context-support module
			Resource mappingLocation = new ClassPathResource("org/springframework/mail/javamail/mime.types");
			if (mappingLocation.exists()) {
				InputStream inputStream = null;
				try {
					inputStream = mappingLocation.getInputStream();
					return new MimetypesFileTypeMap(inputStream);
				}
				catch (IOException ex) {
					// ignore
				}
				finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						}
						catch (IOException ex) {
							// ignore
						}
					}
				}
			}
			return FileTypeMap.getDefaultFileTypeMap();
		}

		public static MediaType getMediaType(Resource resource) {
			String mediaType = fileTypeMap.getContentType(resource.getFilename());
			return (StringUtils.hasText(mediaType) ? MediaType.parseMediaType(mediaType) : null);
		}
	}*/

}
