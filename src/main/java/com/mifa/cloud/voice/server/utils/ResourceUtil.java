package com.mifa.cloud.voice.server.utils;

import com.mifa.cloud.voice.server.commons.dto.ResourceDto;
import com.mifa.cloud.voice.server.commons.dto.RoleResourceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceUtil {

	/**
	 * 只获取拥有的权限
	 * @return
	 */
	public static List<ResourceDto> getResource(List<ResourceDto> parentResourceList, Map<Long, RoleResourceDto> map){
		List<ResourceDto> resources=new ArrayList<ResourceDto>();
		if (parentResourceList!=null&&map!=null) {
            ResourceDto hasResourceParent=null;
			for (ResourceDto resource : parentResourceList) {
				if (map.get(resource.getId()) != null) {
				    hasResourceParent=new ResourceDto();
					hasResourceParent.setId(resource.getId());
					hasResourceParent.setResourceName(resource.getResourceName());
					hasResourceParent.setResourceUrl(resource.getResourceUrl());
					hasResourceParent.setResourceIcon(resource.getResourceIcon());
					List<ResourceDto> childResources=new ArrayList<>();
					for (ResourceDto child_resource : resource.getChildResource()) {
						if (map.get(child_resource.getId()) != null) {
                            ResourceDto childResource=new ResourceDto();
							childResource.setId(child_resource.getId());
							childResource.setResourceName(child_resource.getResourceName());
							childResource.setResourceUrl(child_resource.getResourceUrl());
							childResource.setResourceIcon(child_resource.getResourceIcon());
							childResources.add(childResource);
						}
					}
					hasResourceParent.setChildResource(childResources);
					resources.add(hasResourceParent);
				}			
			}
			return resources;		
		}
		return resources;		
	}
	/**
	 * 在权限列表上标记拥有的权限
	 */
	public static void markResource(List<ResourceDto> parentResourceList,Map<Long, RoleResourceDto> map){
		for (ResourceDto resource : parentResourceList) {
			if (map.get(resource.getId()) != null) {
				resource.setHasResource(true);
				for (ResourceDto child_resource : resource.getChildResource()) {
					if (map.get(child_resource.getId()) != null) {
						child_resource.setHasResource(true);
					}
				}
			}			
		}
	}

}
