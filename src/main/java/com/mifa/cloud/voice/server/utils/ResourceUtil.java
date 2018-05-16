package com.mifa.cloud.voice.server.utils;

import com.mifa.cloud.voice.server.commons.dto.ResourceDTO;
import com.mifa.cloud.voice.server.commons.dto.RoleResourceDTO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceUtil {

	/**
	 * 获取资源树
	 * @return
	 */
	public static List<ResourceDTO> getResource(List<ResourceDTO> parentResourceList, Map<Long, RoleResourceDTO> map){
		List<ResourceDTO> resources=new ArrayList<ResourceDTO>();
		if (CollectionUtils.isNotEmpty(parentResourceList)&& !CollectionUtils.sizeIsEmpty(map)) {
            ResourceDTO hasResourceParent=null;
			for (ResourceDTO resource : parentResourceList) {
				if (map.get(resource.getId()) != null) {
				    hasResourceParent=new ResourceDTO();
					hasResourceParent.setId(resource.getId());
					hasResourceParent.setResourceName(resource.getResourceName());
					hasResourceParent.setResourceUrl(resource.getResourceUrl());
					hasResourceParent.setResourceIcon(resource.getResourceIcon());
					List<ResourceDTO> childResources=new ArrayList<>();
					for (ResourceDTO child_resource : resource.getChildResource()) {
						if (map.get(child_resource.getId()) != null) {
                            ResourceDTO childResource=new ResourceDTO();
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
		return parentResourceList;
	}
	/**
	 * 在权限列表上标记拥有的权限
	 */
	public static void markResource(List<ResourceDTO> parentResourceList, Map<Long, RoleResourceDTO> map){
		for (ResourceDTO resource : parentResourceList) {
			if (map.get(resource.getId()) != null) {
				resource.setHasResource(true);
				for (ResourceDTO child_resource : resource.getChildResource()) {
					if (map.get(child_resource.getId()) != null) {
						child_resource.setHasResource(true);
					}
				}
			}			
		}
	}

}
